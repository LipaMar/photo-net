package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import photonet.server.domain.entity.Rate;

import java.util.List;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface RateMapper extends OpinionMapper<Rate> {

    default double mapRatingsToAvg(List<Rate> ratings) {
        return ratings.stream()
                .mapToInt(Rate::getRating)
                .average()
                .orElse(0);
    }

}
