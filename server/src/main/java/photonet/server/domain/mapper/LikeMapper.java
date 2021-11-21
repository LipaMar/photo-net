package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.AppLike;

@Mapper
public interface LikeMapper extends OpinionMapper<AppLike> {
}
