package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.profile.dto.ProfileUpdateDto;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CategoryService categoryService;

    public ProfileDto findProfile(String username) {
        return userRepository.findByUserName(username)
                .map(userMapper::mapToDto)
                .orElseThrow(NotFoundRestException::new);
    }

    @Transactional
    public ProfileDto editProfile(ProfileUpdateDto dto) {
        var dtoUserName = dto.getUserName();
        if (SecurityUtils.loggedUserName().equals(dtoUserName)) {
            var user = userRepository.findByUserName(dtoUserName)
                    .orElseThrow(NotFoundRestException::new);
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
