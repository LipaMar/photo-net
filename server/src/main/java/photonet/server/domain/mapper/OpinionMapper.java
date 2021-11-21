package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.Opinion;

import java.util.List;

@Mapper
public interface OpinionMapper<T extends Opinion> {

    default Long countOpinion(List<T> list) {
        return (long) list.size();
    }

}
