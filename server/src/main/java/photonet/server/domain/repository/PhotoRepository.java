package photonet.server.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import photonet.server.domain.entity.Photo;

@Repository
public interface PhotoRepository extends CrudRepository<Photo,Long> {
}
