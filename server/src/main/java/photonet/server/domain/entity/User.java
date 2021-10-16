package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {

    @Id
    private Long id;
    private String username;
    private String email;
    private String bio;
    private Boolean active;
    @OneToMany(orphanRemoval = true)
    private List<Comment> comments;
    @OneToOne
    private Photo profilePicture;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Rate> rating;

}
