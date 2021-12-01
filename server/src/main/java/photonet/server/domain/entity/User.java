package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import photonet.server.config.Roles;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "LONGTEXT")
    private String bio;
    @Column(nullable = false)
    private Boolean active = true;
    private String password;
    private String role = Roles.USER;
    @OneToMany(orphanRemoval = true)
    private List<Comment> comments;
    @OneToOne
    private Photo profilePicture;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "target")
    private List<Rate> rating;

}
