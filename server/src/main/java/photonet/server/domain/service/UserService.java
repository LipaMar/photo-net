package photonet.server.domain.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Roles;
import photonet.server.core.exception.AlreadyExistRestException;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.PhotoRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.domain.service.upload.FileService;
import photonet.server.webui.dto.LoginDto;
import photonet.server.webui.dto.UserDto;
import photonet.server.webui.dto.UserInfoDto;
import photonet.server.webui.dto.profile.ProfileDto;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PhotoRepository photoRepository;
  private final UserMapper userMapper;
  private final FileService fileService;

  @Transactional
  public void register(UserDto user) {
    if (userRepository.existsByUserNameOrEmail(user.getUserName(), user.getEmail())) {
      throw new AlreadyExistRestException("Użytkownik o podanym loginie już istnieje",
          HttpStatus.CONFLICT);
    }
    User toSave = userMapper.mapUserDtoToUser(user);
    toSave.setRole(Roles.USER);
    toSave.setIsPublic(false);
    userRepository.save(toSave);
  }

  public ProfileDto getLoggedUserProfile() {
    return userRepository.findByUserName(SecurityUtils.loggedUserName())
        .map(userMapper::mapUserToProfileDto)
        .orElseThrow(NotFoundRestException::new);
  }

  public UserDetails getSecurityDetails(String username) {
    return userRepository.findByUserName(username)
        .map(userMapper::mapUserToUserDetails)
        .orElseThrow(NotFoundRestException::new);
  }

  @Transactional
  public void uploadPicture(MultipartFile file) {
    var user = userRepository.findByUserName(SecurityUtils.loggedUserName())
        .orElseThrow(NotFoundRestException::new);
    var filePath = fileService.saveFile(file);
    var photo = savePhoto(filePath);
    user.setProfilePicture(photo);
    userRepository.save(user);
  }

  public LoginDto isLogged() {
    var auth = SecurityUtils.getAuthentication();
    return userRepository.findByUserName(SecurityUtils.loggedUserName())
        .filter((x) -> auth.isAuthenticated())
        .filter((x) -> !(auth instanceof AnonymousAuthenticationToken))
        .map(userMapper::mapToLoginDto)
        .orElse(null);
  }

  @Transactional
  public Photo savePhoto(String filePath) {
    var photo = new Photo();
    photo.setPath(filePath);
    return photoRepository.save(photo);
  }

  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName).orElseThrow(NotFoundRestException::new);
  }

  public User getLoggedUser() {
    return userRepository.findByUserName(SecurityUtils.loggedUserName())
        .orElseThrow(NotFoundRestException::new);
  }

  public List<UserInfoDto> getUsersInfo() {
    return userRepository.findAll().stream().map(userMapper::userToUserInfo)
        .collect(Collectors.toList());
  }

  @Transactional
  public void unban(String userName) {
    userRepository.updateActiveStatus(userName, true);
  }

  @Transactional
  public void ban(String userName) {
    userRepository.updateActiveStatus(userName, false);
  }
}
