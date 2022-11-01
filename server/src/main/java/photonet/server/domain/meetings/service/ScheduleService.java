package photonet.server.domain.meetings.service;

import static photonet.server.core.enums.MeetingStatus.ARCHIVAL;
import static photonet.server.core.enums.MeetingStatus.DELETED;
import static photonet.server.core.enums.MeetingStatus.FREE;
import static photonet.server.core.enums.MeetingStatus.NEW;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import photonet.server.domain.rating.Rate;
import photonet.server.domain.rating.RateRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.BookMeetingDto;
import photonet.server.webui.dto.MeetingDto;
import photonet.server.webui.dto.ScheduleDto;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final UserRepository userRepository;
  private final MeetingRepository meetingRepository;
  private final ScheduleMapper scheduleMapper;
  private final UserService userService;
  private final RateRepository rateRepository;

  public ScheduleDto getScheduleForUser(String userName) {
    var schedule = scheduleRepository.findByOwnerUserName(userName)
        .orElse(mockSchedule());
    return scheduleMapper.scheduleToDto(schedule);
  }

  @Transactional
  public void updateMeetings(String userName) {
    var schedule = scheduleRepository.findByOwnerUserName(userName)
        .orElse(mockSchedule());
    final var meetingsAsClient = meetingRepository.findAllByUserBookedUserName(userName);
    final var meetings = schedule.getMeetings();
    Stream.concat(meetingsAsClient.stream(), meetings.stream())
        .collect(Collectors.toList())
        .forEach(Meeting::ifPastDateThenUpdateStatus);
    meetingRepository.saveAll(meetings);
    meetingRepository.deleteAllByStatus(DELETED);
  }


  private Schedule mockSchedule() {
    var schedule = new Schedule();
    schedule.setDisabled(true);
    schedule.setMeetings(Collections.emptyList());
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
    meetingRepository.removeAllByScheduleAndDateAndStatus(schedule, scheduleDto.getSaveDate(),
        FREE);
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
    if (meeting.getStatus() != FREE) {
      throw new ForbiddenRestException();
    }
    meeting.setSchedule(schedule);
    meeting.setUserBooked(client);
    meeting.setTimeStart(dto.getTimeStart());
    meeting.setDate(dto.getDate());
    meeting.setPrice(dto.getPrice());
    meeting.setStatus(NEW);
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

  public List<MeetingDto> getLoggedUserMeetings() {
    final var loggedUser = userRepository.findByUserName(SecurityUtils.loggedUserName())
        .orElseThrow(NotFoundRestException::new);
    return meetingRepository.findAllByUserBooked(loggedUser)
        .stream()
        .map(scheduleMapper::meetingToDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public void updateMeetingStatus(Long meetingId, MeetingStatus status) {
    final var meeting = meetingRepository.findById(meetingId).orElseThrow();
    Meeting.ifPastDateThenUpdateStatus(meeting);
    if (meeting.getStatus() == ARCHIVAL || meeting.getStatus() == DELETED) {
      return;
    }
    switch (status) {
      case CANCELED -> cancelMeeting(meeting);
      case ACCEPTED -> meetingRepository.updateMeetingStatus(meetingId, status);
    }
  }

  @Transactional
  public void rateMeeting(Long meetingId, int rating) {
    if (rating <= 5 && rating > 0) {
      meetingRepository.findById(meetingId)
          .filter(meeting -> meeting.getRate() == null)
          .filter(meeting -> meeting.getStatus() == ARCHIVAL)
          .ifPresent(meeting -> {
            meeting.setRate(rating);
            meetingRepository.save(meeting);
            addRatingToUser(meeting, rating);
          });
    }
  }

  private void addRatingToUser(Meeting meeting, int rating) {
    final var owner = meeting.getSchedule().getOwner();
    final var rate = new Rate();
    rate.setRating(rating);
    rate.setAuthor(userRepository.findByUserName(SecurityUtils.loggedUserName()).orElseThrow());
    rate.setTarget(owner);
    rateRepository.save(rate);
  }

  private void cancelMeeting(Meeting meeting) {
    if (SecurityUtils.isLoggedUser(meeting.getUserBooked().getUserName())) {
      meeting.setUserBooked(null);
      meeting.setStatus(FREE);
      meetingRepository.save(meeting);
    } else {
      meetingRepository.delete(meeting);
    }
  }
}
