package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.entity.User;
import photonet.server.domain.service.DiscoverService;
import photonet.server.webui.dto.ProfileBasicDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.DISCOVER)
public class DiscoverController {

    private final DiscoverService service;

    @GetMapping
    public Page<ProfileBasicDto> getProfiles(
            @And({
                    @Spec(path = "active", defaultVal = "true", spec = Equal.class),
                    @Spec(path = "userName", spec = LikeIgnoreCase.class),
                    @Spec(path = "rating", params = "ratingMoreThan", spec = GreaterThanOrEqual.class),
                    @Spec(path = "rating", params = "ratingLessThan", spec = LessThanOrEqual.class),
                    @Spec(path = "ratingCount", params = "rateCountMoreThan", spec = GreaterThanOrEqual.class),
                    @Spec(path = "ratingCount", params = "rateCountLessThan", spec = LessThanOrEqual.class)
            })
                    Specification<User> specification, Pageable pageable) {
        return service.findAll(specification, pageable);
    }

}
