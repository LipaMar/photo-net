package photonet.server.domain.meetings.entity;

import lombok.*;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isDisabled = false;
    @OneToMany(mappedBy = "schedule")
    private List<Meeting> meetings;
    @OneToOne
    private User owner;

}
