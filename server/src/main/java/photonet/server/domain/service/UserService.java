package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import photonet.server.config.exception.ResourceNotFoundException;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.ProfileBasicDto;
import photonet.server.webui.dto.ProfileDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<ProfileBasicDto> getBasicProfileList(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        return users.map(userMapper::mapUserToBasicProfile);
    }

    public ProfileDto getProfile(String userName) {
        return userRepository.findByUserName(userName)
                .map(userMapper::mapUserToProfileDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public UserDetails getDetails(String username){
        return userRepository.findByUserName(username)
                .map(userMapper::mapUserToUserDetails)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
