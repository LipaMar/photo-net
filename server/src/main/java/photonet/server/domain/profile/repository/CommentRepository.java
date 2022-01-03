package photonet.server.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
