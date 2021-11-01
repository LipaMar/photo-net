package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.ProfileBasicDto;
import photonet.server.webui.dto.ProfileDto;

@Mapper(uses = {RateMapper.class})
public interface UserMapper {

    @Mapping(source = "profilePicture.path", target = "profilePicture")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileBasicDto mapUserToBasicProfile(User user);

    @Mapping(source = "profilePicture.path", target = "profilePicture")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileDto mapUserToProfileDto(User user);

    default UserDetails mapUserToUserDetails(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
