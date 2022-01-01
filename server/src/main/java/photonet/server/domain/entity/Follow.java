package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Follow extends Opinion {

    @ManyToMany
    private List<Post> newPosts;

}
