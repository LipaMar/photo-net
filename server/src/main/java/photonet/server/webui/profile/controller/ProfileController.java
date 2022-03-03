package photonet.server.webui.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.ProfileService;
import photonet.server.webui.profile.dto.ProfileBasicDto;
import photonet.server.webui.profile.dto.ProfileDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.PROFILES)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userName}")
    public ProfileBasicDto getDetails(@PathVariable String userName, @RequestParam(required = false) boolean simple) {
        if(simple){
            return profileService.getBasicProfile(userName);
        }
        return profileService.findProfile(userName);
    }

}
