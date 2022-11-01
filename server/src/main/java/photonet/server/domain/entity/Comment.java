package photonet.server.domain.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Comment extends Opinion {

  @Column(columnDefinition = "TEXT")
  private String content;
  private Date added;
  private boolean anonymous;

}
