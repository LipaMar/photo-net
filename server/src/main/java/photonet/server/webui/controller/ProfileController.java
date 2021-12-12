package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.ProfileService;
import photonet.server.webui.dto.ProfileDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.PROFILES)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ProfileDto getDetails(@PathVariable String username) {
        return profileService.findProfile(username);
    }

}
