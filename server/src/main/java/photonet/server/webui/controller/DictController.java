package photonet.server.webui.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.entity.Category;
import photonet.server.domain.repository.CategoryRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.DICTIONARY)
public class DictController {

  private final CategoryRepository categoryRepo;

  @GetMapping(Endpoints.CATEGORIES)
  public List<String> getCategories() {
    return categoryRepo.findAll()
        .stream()
        .map(Category::getName)
        .collect(Collectors.toList());
  }

}
