package photonet.server.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import photonet.server.domain.entity.Category;
import photonet.server.domain.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findCategoriesByName() {

        Mockito.when(categoryRepository.findByName("name")).thenReturn(Optional.of(new Category()));
        final var listSize = categoryService.findCategoriesByName(List.of("name")).size();
        Assertions.assertEquals(1, listSize);

    }
}
