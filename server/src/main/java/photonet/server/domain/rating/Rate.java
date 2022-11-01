package photonet.server.domain.rating;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import photonet.server.domain.entity.Opinion;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Rate extends Opinion {

  private Integer rating;

}
