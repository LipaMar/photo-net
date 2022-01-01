package photonet.server.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.Photographer;

import java.util.Optional;

public interface PhotographerRepository extends JpaRepository<Photographer, Long>, JpaSpecificationExecutor<Photographer> {

    Optional<Photographer> findByUserUserName(String username);

    default Specification<Photographer> notLoggedUser(){
        return (root, criteriaQuery, criteriaBuilder) -> {
          var userName = root.get("user").get("userName");
          return criteriaBuilder.notEqual(userName, SecurityUtils.loggedUserName());
        };
    }
}
