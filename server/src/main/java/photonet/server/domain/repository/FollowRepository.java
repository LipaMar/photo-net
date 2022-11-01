package photonet.server.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

  boolean existsByAuthorUserNameAndTargetUserName(String author, String target);

  Follow findByAuthorUserNameAndTargetUserName(String author, String target);

  List<Follow> findAllByAuthorUserName(String followingUser);

  List<Follow> findAllByTargetUserName(String followedUser);

  long countAllByTargetUserName(String followedUser);

}
