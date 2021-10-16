package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.ProfilesBasicDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final UserMapper userMapper;

    public Page<ProfilesBasicDto> getBasicProfileList(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        return users.map(userMapper::mapUserPhotoToBasicProfile);
    }


}
