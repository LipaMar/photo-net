package photonet.server.domain.meetings.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import photonet.server.domain.entity.User;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
