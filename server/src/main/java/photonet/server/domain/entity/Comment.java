package photonet.server.domain.entity;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    private Long id;
    @OneToOne
    private User author;
    @ManyToOne
    private User target;

}
