package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.entity.Photographer;
import photonet.server.domain.service.DiscoverService;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.DISCOVER)
public class DiscoverController {

    private final DiscoverService service;

    @GetMapping
    public Page<PhotographerBasicDto> getProfiles(
            @And({
                    @Spec(path = "user.active", defaultVal = "true", spec = Equal.class),
                    @Spec(path = "user", spec = NotNull.class),
                    @Spec(path = "user.userName", spec = LikeIgnoreCase.class),
                    @Spec(path = "user.userName", spec = LikeIgnoreCase.class),
                    @Spec(path = "user.rating", params = "ratingMoreThan", spec = GreaterThanOrEqual.class),
                    @Spec(path = "user.rating", params = "ratingLessThan", spec = LessThanOrEqual.class),
                    @Spec(path = "user.ratingCount", params = "rateCountMoreThan", spec = GreaterThanOrEqual.class),
                    @Spec(path = "user.ratingCount", params = "rateCountLessThan", spec = LessThanOrEqual.class)
            })
                    Specification<Photographer> specification, Pageable pageable) {
        return service.findAll(specification, pageable);
    }

}
