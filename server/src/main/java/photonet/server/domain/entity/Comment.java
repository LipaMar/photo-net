package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "comment")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment extends Opinion {

    private String content;

}
