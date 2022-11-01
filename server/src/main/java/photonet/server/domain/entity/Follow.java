package photonet.server.domain.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Follow extends Opinion {

  @ManyToMany
  private List<Post> newPosts;

}
