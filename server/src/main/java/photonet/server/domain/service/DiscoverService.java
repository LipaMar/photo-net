package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import photonet.server.domain.entity.User;
import photonet.server.domain.mapper.UserMapper;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final UserRepository photographerRepository;
    private final UserMapper photographerMapper;

    public Page<PhotographerBasicDto> findAll(Specification<User> specification, Pageable pageable,
                                              List<String> categories) {
        specification = specification.and(photographerRepository.notLoggedUser());
        if (categories != null) {
            return filterByCategories(specification, pageable, categories);
        }
        return photographerRepository.findAll(specification, pageable)
                .map(photographerMapper::mapToBasicProfile);
    }

    private Page<PhotographerBasicDto> filterByCategories(Specification<User> specification,
                                                          Pageable pageable,
                                                          List<String> categories) {
        var list = photographerRepository.findAll(specification, pageable.getSort())
                .stream()
                .map(photographerMapper::mapToBasicProfile)
                .filter(dto -> dto.getCategories().containsAll(categories))
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, list.size());
    }

    //TODO: obsłużyć filtry przez criteria api
    private Specification<User> filterByCategories(List<String> categories) {
        return (photographer, query, cb) -> {
            if (categories == null) {
                return cb.and();
            }
            List<Predicate> predicates = new ArrayList<>();
            var categoryNames = photographer.join("categories");
            for (String category : categories) {
                predicates.add(cb.equal(categoryNames.get("name"), category));
            }
            var pred = predicates.toArray(Predicate[]::new);
            return cb.and(pred);
        };
    }

}
