package photonet.server.domain.meetings.entity;

import lombok.*;
import photonet.server.core.enums.MeetingStatus;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private MeetingStatus status;
    private BigDecimal price;
    private Integer rate;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private User userBooked;


}
