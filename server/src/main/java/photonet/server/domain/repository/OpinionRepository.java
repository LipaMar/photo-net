package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import photonet.server.domain.entity.Follow;
import photonet.server.domain.entity.Opinion;

import java.util.List;

public interface OpinionRepository<T extends Opinion> extends JpaRepository<Follow, Long> {

    boolean existsByAuthorUserNameAndTargetUserName(String author, String target);

    T findByAuthorUserNameAndTargetUserName(String author, String target);

    @Query("select f from Follow f where f.author.userName = :followingUser")
    List<T> findAllFollowingByUserName(String followingUser);

    @Query("select f from Follow f where f.target.userName = :followedUser")
    List<T> findAllFollowersByUserName(String followedUser);

}
