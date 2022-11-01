package photonet.server.domain.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import photonet.server.domain.entity.Opinion;

@Mapper
public interface OpinionMapper<T extends Opinion> {

  default Long countOpinion(List<T> list) {
    return (long) list.size();
  }

}
