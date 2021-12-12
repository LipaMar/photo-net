package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import photonet.server.domain.entity.Photographer;
import photonet.server.domain.mapper.PhotographerMapper;
import photonet.server.domain.repository.PhotographerRepository;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final PhotographerRepository photographerRepository;
    private final PhotographerMapper photographerMapper;

    public Page<PhotographerBasicDto> findAll(Specification<Photographer> specification, Pageable pageable) {
        return photographerRepository.findAll(specification, pageable)
                .map(photographerMapper::mapToBasicProfile);
    }

}
