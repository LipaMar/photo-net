package photonet.server.domain.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import photonet.server.domain.rating.Rate;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface RateMapper extends OpinionMapper<Rate> {

  default double mapRatingsToAvg(List<Rate> ratings) {
    return ratings.stream()
        .mapToInt(Rate::getRating)
        .average()
        .stream()
        .map(rate -> Math.round(rate * 100d) / 100d)
        .findFirst()
        .orElse(0);
  }

}
