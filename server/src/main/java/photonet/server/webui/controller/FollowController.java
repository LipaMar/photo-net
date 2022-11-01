package photonet.server.webui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.service.FollowService;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.FOLLOW)
public class FollowController {

  private final FollowService followService;

  @GetMapping()
  public boolean isFollowedByLoggedUser(@RequestParam String userName) {
    return followService.isFollowedByLoggedUser(userName);
  }

  @PostMapping()
  public ResponseEntity<?> follow(@RequestParam String userName) {
    followService.follow(userName);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  public ResponseEntity<?> unfollow(@RequestParam String userName) {
    followService.unfollow(userName);
    return ResponseEntity.noContent().build();
  }

}
