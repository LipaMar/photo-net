package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.ProfilesBasicDto;

@Mapper
public interface UserMapper {

//    @AfterMapping
//    default void toProfilePic(@Source Photo photo, @Source User user, @Target ProfilesBasicDto profileBasicDto){
//
//    }

    ProfilesBasicDto mapUserPhotoToBasicProfile(User user);

}
