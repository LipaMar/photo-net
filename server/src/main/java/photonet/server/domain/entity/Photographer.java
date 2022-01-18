package photonet.server.domain.entity;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Photographer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private String city;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @ManyToMany
    @NotEmpty
    private List<Category> categories;

}
