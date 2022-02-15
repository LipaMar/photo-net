package photonet.server.domain.meetings.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.meetings.ScheduleMapper;
import photonet.server.domain.meetings.entity.Meeting;
import photonet.server.domain.meetings.entity.Schedule;
import photonet.server.domain.meetings.repository.MeetingRepository;
import photonet.server.domain.meetings.repository.ScheduleRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.BookMeetingDto;
import photonet.server.webui.dto.MeetingDto;
import photonet.server.webui.dto.ScheduleDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDto getScheduleForUser(String userName) {
        var schedule = scheduleRepository.findByOwnerUserName(userName)
                                         .orElse(mockSchedule());
        return scheduleMapper.scheduleToDto(schedule);
    }

    private Schedule mockSchedule() {
        var schedule = new Schedule();
        schedule.setDisabled(true);
        return schedule;
    }

    @Transactional
    public void createFreeEvent(ScheduleDto scheduleDto) {
        var owner = scheduleDto.getOwner();
        if (SecurityUtils.isNotLoggedUser(owner)) {
            throw new ForbiddenRestException();
        }
        List<Meeting> meetings = scheduleDto.getMeetings()
                                            .stream()
                                            .map(scheduleMapper::MeetingDtoToEntity)
                                            .collect(Collectors.toList());
        final var schedule = scheduleRepository.findByOwnerUserName(owner)
                                               .orElse(getNewSchedule(owner));
        scheduleRepository.save(schedule);
        meetings.forEach(meeting -> meeting.setSchedule(schedule));
        meetingRepository.removeAllByScheduleAndDateAndStatus(schedule, scheduleDto.getSaveDate(), MeetingStatus.FREE);
        meetingRepository.saveAll(meetings);
    }

    private Schedule getNewSchedule(String owner) {
        final var user = userRepository.findByUserName(owner)
                                       .orElseThrow(NotFoundRestException::new);
        return Schedule.builder()
                       .owner(user)
                       .isDisabled(false)
                       .build();
    }

    @Transactional
    public void bookMeeting(BookMeetingDto dto) {
        final var schedule = scheduleRepository.findByOwnerUserName(dto.getPhotographer())
                                               .orElseThrow(NotFoundRestException::new);
        final var client = userRepository.findByUserName(SecurityUtils.loggedUserName())
                                         .orElseThrow(NotFoundRestException::new);
        final var meeting = meetingRepository.findById(dto.getId())
                                             .orElseThrow(NotFoundRestException::new);
        meeting.setSchedule(schedule);
        meeting.setUserBooked(client);
        meeting.setTimeStart(dto.getTimeStart());
        meeting.setDate(dto.getDate());
        meeting.setPrice(dto.getPrice());
        meeting.setStatus(MeetingStatus.NEW);
        meetingRepository.save(meeting);
    }

    public MeetingDto getMeetingById(Long id) {
        return meetingRepository.findById(id)
                                .map(scheduleMapper::meetingToDto)
                                .orElseThrow(NotFoundRestException::new);
    }

    public MeetingDto getMeetingByDateAndHour(String owner, String dateAsString, LocalTime hour) {
        final var date = LocalDate.parse(dateAsString);
        final var schedule = scheduleRepository.findByOwnerUserName(owner)
                                               .orElseThrow(NotFoundRestException::new);
        final var meeting = schedule.getMeetings()
                                    .stream()
                                    .filter(m -> isEqual(m, date, hour))
                                    .findFirst()
                                    .orElseThrow(NotFoundRestException::new);
        return scheduleMapper.meetingToDto(meeting);
    }

    private boolean isEqual(Meeting meeting, LocalDate date, LocalTime hour) {
        return meeting.getDate().isEqual(date) && meeting.getTimeStart().equals(hour);
    }

    public List<MeetingDto> getLoggedUSerMeetings() {
        final var loggedUser = userRepository.findByUserName(SecurityUtils.loggedUserName())
                                             .orElseThrow(NotFoundRestException::new);
        return meetingRepository.findAllByUserBooked(loggedUser)
                                .stream()
                                .map(scheduleMapper::meetingToDto)
                                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMeetingStatus(Long meetingId, MeetingStatus status) {
        meetingRepository.updateMeetingStatus(meetingId, status);
    }
}
