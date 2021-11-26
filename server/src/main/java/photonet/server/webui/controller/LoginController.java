package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.LoginDto;

@RestController
@RequestMapping(Endpoints.LOGIN)
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<LoginDto> logged() {
        return ResponseEntity.ok(userService.isLogged());
    }

}
