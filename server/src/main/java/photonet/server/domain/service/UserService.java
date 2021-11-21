package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.core.exception.AlreadyExistRestException;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.PhotoRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.ProfileBasicDto;
import photonet.server.webui.dto.ProfileDto;
import photonet.server.webui.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final UserMapper userMapper;
    private final FileService fileService;

    public Page<ProfileBasicDto> getBasicProfileList(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        return users.map(userMapper::mapUserToBasicProfile);
    }

    @Transactional
    public void register(UserDto user) {
        if (userRepository.existsByUserNameOrEmail(user.getUserName(), user.getEmail())) {
            throw new AlreadyExistRestException();
        }
        User toSave = userMapper.mapUserDtoToUser(user);
        userRepository.save(toSave);
    }

    public ProfileDto getProfile(String userName) {
        return userRepository.findByUserName(userName)
                .map(userMapper::mapUserToProfileDto)
                .orElseThrow(NotFoundRestException::new);
    }

    public ProfileDto getLoggedUserProfile() {
        return userRepository.findByUserName(loggedUserName())
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
        var user = userRepository.findByUserName(loggedUserName())
                .orElseThrow(NotFoundRestException::new);
        var filePath = fileService.saveFile(file);
        var photo = photoRepository.save(buildPhoto(filePath));
        user.setProfilePicture(photo);
        userRepository.save(user);
    }

    private Photo buildPhoto(String filePath) {
        var photo = new Photo();
        photo.setPath(filePath);
        return photo;
    }

    private String loggedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
