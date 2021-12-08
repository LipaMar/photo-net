package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.ProfileBasicDto;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<ProfileBasicDto> findAll(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable)
                .map(userMapper::mapUserToBasicProfile);
    }

}
