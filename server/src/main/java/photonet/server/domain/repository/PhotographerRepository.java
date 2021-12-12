package photonet.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import photonet.server.domain.entity.Photographer;

import java.util.Optional;

public interface PhotographerRepository extends JpaRepository<Photographer, Long>, JpaSpecificationExecutor<Photographer> {

    Optional<Photographer> findByUserUserName(String username);

}
