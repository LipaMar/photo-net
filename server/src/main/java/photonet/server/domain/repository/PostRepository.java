package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthor(User user);

}
