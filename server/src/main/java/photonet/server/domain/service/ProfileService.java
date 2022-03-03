package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.profile.dto.ProfileBasicDto;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.profile.dto.ProfileUpdateDto;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CategoryService categoryService;

    public ProfileDto findProfile(String userName) {
        return userMapper.mapToDto(userService.findByUserName(userName));
    }

    public ProfileBasicDto getBasicProfile(String userName) {
        return userMapper.mapUserToBasicProfile(userService.findByUserName(userName));
    }

    @Transactional
    public ProfileDto editProfile(ProfileUpdateDto dto) {
        var dtoUserName = dto.getUserName();
        if (SecurityUtils.loggedUserName().equals(dtoUserName)) {
            var user = userService.findByUserName(dtoUserName);
            var saved = userRepository.save(updateUser(user, dto));
            return userMapper.mapToDto(saved);
        } else {
            throw new ForbiddenRestException();
        }
    }

    private User updateUser(User photographer, ProfileUpdateDto dto) {
        var categories = categoryService.findCategoriesByName(dto.getCategories());
        return photographer.toBuilder().bio(dto.getBio())
                           .city(dto.getCity())
                           .price(dto.getPrice())
                           .categories(categories)
                           .build();
    }

}
