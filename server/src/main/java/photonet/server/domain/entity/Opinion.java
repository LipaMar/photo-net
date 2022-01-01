package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private User target;

}
