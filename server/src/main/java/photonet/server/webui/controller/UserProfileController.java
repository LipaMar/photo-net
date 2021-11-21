package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.ProfileDto;

@RestController
@RequestMapping(Endpoints.USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public ProfileDto profile() {
        return userService.getLoggedUserProfile();
    }

    @PostMapping
    public void uploadProfilePic(MultipartFile picture){
        userService.uploadPicture(picture);
    }



}
