package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.Rate;

import java.util.List;

@Mapper
public interface RateMapper {

    default double mapRatingsToAvg(List<Rate> ratings) {
        return ratings.stream()
                .mapToInt(Rate::getRating)
                .average()
                .orElse(0);
    }

}
