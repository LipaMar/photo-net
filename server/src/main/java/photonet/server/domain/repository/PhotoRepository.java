package photonet.server.domain.repository;

import org.springframework.data.repository.CrudRepository;
import photonet.server.domain.entity.Photo;

public interface PhotoRepository extends CrudRepository<Photo,Long> {
}
