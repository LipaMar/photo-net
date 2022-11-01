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
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.PhotoRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.domain.service.upload.FileService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PhotoRepository photoRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private FileService fileService;
  @InjectMocks
  private UserService userService;

  @Test
  void getLoggedUser() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    Mockito.when(userRepository.findByUserName(any())).thenReturn(Optional.of(new User()));
    Assertions.assertNotNull(userService.getLoggedUser());
  }
}
