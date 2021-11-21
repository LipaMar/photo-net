package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.UserDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.PROFILES)
public class ProfileController {

    private final UserService userService;

    @PostMapping(Endpoints.REGISTER)
    public void register(UserDto user) { //TODO: walidacja
        userService.register(user);
    }


}
