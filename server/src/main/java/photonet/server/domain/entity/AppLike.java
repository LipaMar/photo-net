package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppLike extends Opinion {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
