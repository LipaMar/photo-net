package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.domain.mapper.PhotographerMapper;
import photonet.server.domain.repository.PhotographerRepository;
import photonet.server.webui.dto.ProfileDto;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final PhotographerRepository photographerRepository;
    private final PhotographerMapper photographerMapper;

    public ProfileDto findProfile(String username) {
        return photographerRepository.findByUserUserName(username)
                .map(photographerMapper::mapToDto)
                .orElseThrow(NotFoundRestException::new);
    }
}
