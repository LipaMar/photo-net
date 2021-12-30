package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
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
    @OneToMany(orphanRemoval = true, mappedBy = "target")
    private List<Comment> comments;
    @OneToMany(orphanRemoval = true, mappedBy = "author")
    private List<Comment> commentsAuthored;
    @OneToOne
    private Photo profilePicture;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "target")
    private List<Rate> ratings;
    @OneToOne(orphanRemoval = true)
    private Photographer photographer;

    @Formula("(select count(r.id) from rate r where r.target_id = id)")
    private Integer ratingCount;
    @Formula("(select avg(CAST(r.rating as double)) from rate r where r.target_id = id)")
    private Double rating;

}
