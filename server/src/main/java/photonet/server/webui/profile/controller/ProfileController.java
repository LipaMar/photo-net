package photonet.server.webui.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.ProfileService;
import photonet.server.webui.profile.dto.ProfileBasicDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.PROFILES)
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/{userName}")
  public ProfileBasicDto getDetails(@PathVariable String userName,
      @RequestParam(required = false) boolean simple) {
    if (simple) {
      return profileService.getBasicProfile(userName);
    }
    return profileService.findProfile(userName);
  }

}
