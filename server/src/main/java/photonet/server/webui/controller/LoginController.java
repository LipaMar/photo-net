package photonet.server.webui.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;

@RestController
@RequestMapping(Endpoints.LOGIN)
public class LoginController {

    @GetMapping
    public ResponseEntity<Boolean> logged() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken));
    }
}
