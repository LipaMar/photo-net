package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.ProfilesBasicDto;

@Mapper(uses = {PhotoMapper.class, RateMapper.class})
public interface UserMapper {

    ProfilesBasicDto mapUserPhotoToBasicProfile(User user);

}
