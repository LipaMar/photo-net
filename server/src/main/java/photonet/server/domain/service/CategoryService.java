package photonet.server.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.domain.entity.Category;
import photonet.server.domain.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> findCategoriesByName(List<String> categoryNames) {
        List<Category> result = new ArrayList<>();
        categoryNames.forEach(name -> result.add(categoryRepository.findByName(name).orElseThrow(NotFoundRestException::new)));
        return result;
    }
}
