package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.ProfileBasicDto;

@Mapper(uses = {RateMapper.class})
public interface UserMapper {

    @Mapping(source = "profilePicture.path", target = "profilePicture")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileBasicDto mapUserToBasicProfile(User user);

}
