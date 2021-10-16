package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photonet.server.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
