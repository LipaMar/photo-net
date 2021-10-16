package photonet.server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Post {

    @Id
    private Long id;
    @OneToOne
    private Photo photo;
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private User author;

}

