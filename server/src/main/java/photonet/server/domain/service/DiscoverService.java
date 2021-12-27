package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import photonet.server.domain.entity.Category;
import photonet.server.domain.entity.Photographer;
import photonet.server.domain.mapper.PhotographerMapper;
import photonet.server.domain.repository.PhotographerRepository;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

import javax.persistence.criteria.Expression;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoverService {

    private final PhotographerRepository photographerRepository;
    private final PhotographerMapper photographerMapper;

    public Page<PhotographerBasicDto> findAll(Specification<Photographer> specification, Pageable pageable, List<String> categories) {
        var specs = Specification.where(specification).and(filterByCategories(categories));
        return photographerRepository.findAll(specs, pageable)
                .map(photographerMapper::mapToBasicProfile);
    }

    private Specification<Photographer> filterByCategories(List<String> categories) {
        return (root, query, builder) -> {

//            query.distinct(true);
//            Root<Cat> cat = root;
//            Subquery<Owner> ownerSubQuery = query.subquery(Owner.class);
//            Root<Owner> owner = ownerSubQuery.from(Owner.class);
//            Expression<Collection<Cat>> ownerCats = owner.get("cats");
//            ownerSubQuery.select(owner);
//            ownerSubQuery.where(cb.equal(owner.get("name"), ownerName), cb.isMember(cat, ownerCats));
//            return cb.exists(ownerSubQuery);

            query.distinct(true);
            var photographer = root;
            var photographerSubQuery = query.subquery(Photographer.class);
            var categorySubQuery = query.subquery(Category.class);
            var category = categorySubQuery.from(Category.class);
            Expression<Collection<Category>> categoriesOfPhotographer = photographer.get("categories");
            photographerSubQuery.select(photographer);
            photographerSubQuery.where(categoriesOfPhotographer.in(categories));
            return builder.exists(photographerSubQuery);
        };
    }

}
