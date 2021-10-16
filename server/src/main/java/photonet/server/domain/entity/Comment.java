package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    private Long id;
    @OneToOne
    private User author;
    @ManyToOne
    private User target;

}
