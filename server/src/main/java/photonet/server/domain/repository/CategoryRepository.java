package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
