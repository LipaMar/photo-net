package photonet.server.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;
    private String username;
    private String email;
    private String bio;
    private Boolean active;
    @OneToMany(orphanRemoval = true)
    private List<Comment> comments;


}
