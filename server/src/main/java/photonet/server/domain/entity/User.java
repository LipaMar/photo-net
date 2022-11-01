package photonet.server.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import photonet.server.domain.rating.Rate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name = "app_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String userName;
  @Column(unique = true)
  private String email;
  @Column(length = 4000)
  private String bio;
  @Column(nullable = false)
  private Boolean active;
  @Column(nullable = false)
  private Boolean isPublic;
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
  @Formula("(select avg(r.rating) from rate r where r.target_id = id)")
  private Double rating;

}
