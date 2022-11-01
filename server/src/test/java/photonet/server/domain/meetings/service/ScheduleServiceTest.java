package photonet.server.domain.meetings.service;

import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.meetings.entity.Meeting;

class ScheduleServiceTest {

  private Meeting meeting;
  private final LocalDate now = LocalDate.of(2022, 5, 15);
  private final LocalDate past = LocalDate.of(2022, 5, 1);
  private final LocalDate future = LocalDate.of(2022, 5, 20);


  @BeforeEach
  void setUp() {
    meeting = new Meeting();
  }

  @Test
  void ifNewAndPastDateThenStatusShouldBeArchival() {
    meeting.setStatus(MeetingStatus.NEW);
    try (MockedStatic<LocalDate> mock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
      mock.when(LocalDate::now).thenReturn(now);
      meeting.setDate(past);
      Meeting.ifPastDateThenUpdateStatus(meeting);
      Assertions.assertEquals(MeetingStatus.ARCHIVAL, meeting.getStatus());
    }
  }

  @Test
  void ifFreeAndPastDateThenStatusShouldBeDeleted() {
    meeting.setStatus(MeetingStatus.FREE);
    try (MockedStatic<LocalDate> mock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
      mock.when(LocalDate::now).thenReturn(now);
      meeting.setDate(past);
      Meeting.ifPastDateThenUpdateStatus(meeting);
      Assertions.assertEquals(MeetingStatus.DELETED, meeting.getStatus());
    }
  }

  @Test
  void ifFreeAndFutureDateThenStatusShouldBeFree() {
    meeting.setStatus(MeetingStatus.FREE);
    try (MockedStatic<LocalDate> mock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
      mock.when(LocalDate::now).thenReturn(now);
      meeting.setDate(future);
      Meeting.ifPastDateThenUpdateStatus(meeting);
      Assertions.assertEquals(MeetingStatus.FREE, meeting.getStatus());
    }
  }

  @Test
  void ifNewAndFutureDateThenStatusShouldBeNew() {
    meeting.setStatus(MeetingStatus.NEW);
    try (MockedStatic<LocalDate> mock = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
      mock.when(LocalDate::now).thenReturn(now);
      meeting.setDate(future);
      Meeting.ifPastDateThenUpdateStatus(meeting);
      Assertions.assertEquals(MeetingStatus.NEW, meeting.getStatus());
    }
  }

  @Test
  void ifNewAndPresentDateThenStatusShouldBeNew() {
    meeting.setStatus(MeetingStatus.NEW);
    final var timeNow = LocalTime.of(12, 0);
    meeting.setTimeStart(timeNow);
    try (MockedStatic<LocalDate> mockDate = mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS);
        MockedStatic<LocalTime> mockTime = mockStatic(LocalTime.class,
            Mockito.CALLS_REAL_METHODS)) {
      mockDate.when(LocalDate::now).thenReturn(now);
      mockTime.when(LocalTime::now).thenReturn(timeNow);
      meeting.setDate(now);
      Meeting.ifPastDateThenUpdateStatus(meeting);
      Assertions.assertEquals(MeetingStatus.NEW, meeting.getStatus());
    }
  }

}
