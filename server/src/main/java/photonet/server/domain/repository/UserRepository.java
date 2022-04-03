package photonet.server.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserName(String userName);

    Boolean existsByUserNameOrEmail(String userName, String email);

    default Specification<User> notLoggedUser() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var userName = root.get("userName");
            return criteriaBuilder.notEqual(userName, SecurityUtils.loggedUserName());
        };
    }

    @Modifying
    @Query("update User u set u.active = :isActive where u.userName = :userName")
    void updateActiveStatus(String userName, boolean isActive);

}
