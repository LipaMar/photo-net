package photonet.server.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppLike extends Opinion {

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

}
