package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.Photographer;
import photonet.server.domain.mapper.PhotographerMapper;
import photonet.server.domain.repository.PhotographerRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.profile.dto.ProfileUpdateDto;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final PhotographerRepository photographerRepository;
    private final UserRepository userRepository;
    private final PhotographerMapper photographerMapper;
    private final CategoryService categoryService;

    public ProfileDto findProfile(String username) {
        return photographerRepository.findByUserUserName(username)
                .map(photographerMapper::mapToDto)
                .orElseThrow(NotFoundRestException::new);
    }

    @Transactional
    public ProfileDto editProfile(ProfileUpdateDto dto) {
        var dtoUserName = dto.getUserName();
        if (SecurityUtils.loggedUserName().equals(dtoUserName)) {
            var photographer = photographerRepository.findByUserUserName(dtoUserName)
                    .orElseThrow(NotFoundRestException::new);
            var saved = photographerRepository.save(updatePhotographer(photographer, dto));
            return photographerMapper.mapToDto(saved);
        } else {
            throw new ForbiddenRestException();
        }
    }

    private Photographer updatePhotographer(Photographer photographer, ProfileUpdateDto dto) {
        var categories = categoryService.findCategoriesByName(dto.getCategories());
        var user = photographer.getUser().toBuilder().bio(dto.getBio()).build();
        userRepository.save(user);
        return photographer.toBuilder()
                .city(dto.getCity())
                .price(dto.getPrice())
                .categories(categories)
                .user(user)
                .build();
    }
}
