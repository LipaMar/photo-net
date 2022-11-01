package photonet.server.domain.meetings.entity;

import static photonet.server.core.enums.MeetingStatus.ARCHIVAL;
import static photonet.server.core.enums.MeetingStatus.DELETED;
import static photonet.server.core.enums.MeetingStatus.FREE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.entity.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate date;
  private LocalTime timeStart;
  private MeetingStatus status;
  private BigDecimal price;
  private Integer rate;
  @ManyToOne
  private Schedule schedule;
  @ManyToOne
  private User userBooked;

  public static void ifPastDateThenUpdateStatus(Meeting meeting) {
    final var date = meeting.getDate();
    final var time = meeting.getTimeStart();
    if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && time.isBefore(
        LocalTime.now()))) {
      if (meeting.getStatus() == FREE) {
        meeting.setStatus(DELETED);
      } else if (meeting.getStatus() != DELETED) {
        meeting.setStatus(ARCHIVAL);
      }
    }
  }

}
