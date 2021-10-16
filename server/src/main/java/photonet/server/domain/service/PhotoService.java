package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.config.exception.ResourceNotFoundException;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.entity.User;
import photonet.server.domain.repository.PhotoRepository;
import photonet.server.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public String getProfilePicture(Long userId) {
        return userRepository.findById(userId)
                .map(User::getProfilePicture)
                .map(Photo::getPath)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
