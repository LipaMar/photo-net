package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
public class Opinion {

    @Id
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private User target;

}
