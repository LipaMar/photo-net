package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.ProfileService;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.profile.ProfileDto;
import photonet.server.webui.dto.profile.ProfileUpdateDto;

@RestController
@RequestMapping(Endpoints.USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {

  private final UserService userService;
  private final ProfileService profileService;

  @GetMapping
  public ProfileDto profile() {
    return userService.getLoggedUserProfile();
  }

  @PostMapping
  public void uploadProfilePic(MultipartFile file) {
    userService.uploadPicture(file);
  }

  @PutMapping
  public ProfileDto editProfile(@RequestBody ProfileUpdateDto dto) {
    return profileService.editProfile(dto);
  }
}
