package photonet.server.domain.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.AppLike;
import photonet.server.domain.entity.Opinion;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<AppLike, Long> {

    Optional<AppLike> findByAuthorAndPost(User author, Post post);

    List<AppLike> findAllByAuthorUserName(String followingUser);

    List<AppLike> findAllByTargetUserName(String followedUser);

}
