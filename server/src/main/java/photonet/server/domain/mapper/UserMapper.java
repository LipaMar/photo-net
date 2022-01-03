package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.LoginDto;
import photonet.server.webui.profile.dto.ProfileBasicDto;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.dto.UserDto;

@Mapper(uses = {RateMapper.class, PhotoMapper.class, PostMapper.class})
public interface UserMapper {

    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileBasicDto mapUserToBasicProfile(User user);

    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileDto mapUserToProfileDto(User user);

    User mapUserDtoToUser(UserDto dto);

    LoginDto mapToLoginDto(User user);

    default String mapUserToString(User user){
        return user.getUserName();
    }

    default UserDetails mapUserToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
