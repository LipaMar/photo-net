package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.UserService;
import photonet.server.webui.dto.LoginDto;
import photonet.server.webui.dto.UserDto;
import photonet.server.webui.dto.UserInfoDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping(Endpoints.LOGIN)
    public ResponseEntity<LoginDto> logged() {
        return ResponseEntity.ok(userService.isLogged());
    }

    @GetMapping(Endpoints.USERS)
    public List<UserInfoDto> getUsersInfo() {
        return userService.getUsersInfo();
    }

    @PostMapping(Endpoints.USERS)
    public void ban(@RequestBody String userName) {
        userService.ban(userName);
    }

    @PutMapping(Endpoints.USERS)
    public void unban(@RequestBody String userName) {
        userService.unban(userName);
    }

    @PostMapping(Endpoints.REGISTER)
    public void register(@RequestBody UserDto user) { //TODO: walidacja
        userService.register(user);
    }

}
