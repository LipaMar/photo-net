package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.Photo;

@Mapper
public interface PhotoMapper {

    String mapToString(Photo photo);

}
