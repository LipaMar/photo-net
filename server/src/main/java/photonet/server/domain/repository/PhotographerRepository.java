package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import photonet.server.domain.entity.Photographer;

public interface PhotographerRepository extends JpaRepository<Photographer, Long>, JpaSpecificationExecutor<Photographer> {

}
