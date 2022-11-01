package photonet.server.domain.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import photonet.server.domain.entity.User;
import photonet.server.domain.repository.FollowRepository;
import photonet.server.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

  @Mock
  private FollowRepository followRepository;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private FollowService followService;

  @Test
  void follow() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));
    followService.follow("follow");
    Mockito.verify(followRepository, Mockito.times(1)).save(any());
  }

  @Test
  void unfollow() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(followRepository.existsByAuthorUserNameAndTargetUserName(any(), any()))
        .thenReturn(true);
    followService.unfollow("unfollow");
    Mockito.verify(followRepository, Mockito.times(1)).delete(any());
  }

  @Test
  void countFollowers() {
    Mockito.when(followRepository.countAllByTargetUserName(any())).thenReturn(3L);
    final var countFollowers = followService.countFollowers("username");
    Assertions.assertEquals(3, countFollowers);
  }
}
