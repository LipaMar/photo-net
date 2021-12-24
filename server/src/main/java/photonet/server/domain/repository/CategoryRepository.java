package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import photonet.server.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
