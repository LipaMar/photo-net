package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Follow;
import photonet.server.domain.entity.Opinion;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByAuthorUserNameAndTargetUserName(String author, String target);

    Follow findByAuthorUserNameAndTargetUserName(String author, String target);

    List<Follow> findAllByAuthorUserName(String followingUser);

    List<Follow> findAllByTargetUserName(String followedUser);

}
