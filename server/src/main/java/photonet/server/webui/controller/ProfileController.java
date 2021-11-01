package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.ProfileBasicDto;
import photonet.server.webui.dto.ProfileDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.PROFILE)
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public ProfileDto profile() {
        return userService.getProfile(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
