package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.ProfileService;
import photonet.server.domain.service.UserService;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.profile.dto.ProfileUpdateDto;

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
    public void uploadProfilePic(MultipartFile file){
        userService.uploadPicture(file);
    }
    @PutMapping
    public ProfileDto editProfile(@RequestBody ProfileUpdateDto dto){
        return profileService.editProfile(dto);
    }

}
