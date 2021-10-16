package photonet.server.webui.controller;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.ProfilesBasicDto;


@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.HOME)
public class HomeController {

    private final UserService userService;

    @GetMapping
    public Page<ProfilesBasicDto> home(Pageable pageable) {
        return userService.getBasicProfileList(pageable);
    }

}
