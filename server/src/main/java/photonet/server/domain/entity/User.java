package photonet.server.domain.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import photonet.server.domain.rating.Rate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
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
    private Boolean active;
    private String password;
    private String role;
    private BigDecimal price;
    private String city;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @ManyToMany
    private List<Category> categories;
    @OneToMany(orphanRemoval = true, mappedBy = "target")
    private List<Comment> comments;
    @OneToMany(orphanRemoval = true, mappedBy = "author")
    private List<Comment> commentsAuthored;
    @OneToOne
    private Photo profilePicture;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "target")
    private List<Rate> ratings;

    @Formula("(select count(r.id) from rate r where r.target_id = id)")
    private Integer ratingCount;
    @Formula("(select avg(CAST(r.rating as double)) from rate r where r.target_id = id)")
    private Double rating;

}
