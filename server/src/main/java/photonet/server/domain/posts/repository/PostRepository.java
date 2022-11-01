package photonet.server.domain.posts.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByAuthor(User user);

}
