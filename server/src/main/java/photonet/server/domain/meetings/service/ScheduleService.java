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
import photonet.server.webui.dto.ScheduleDto;

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
}
