package photonet.server.domain.meetings.entity;

import lombok.*;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private int hours;
    private MeetingStatus status;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private User userBooked;


}
