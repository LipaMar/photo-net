package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;
import photonet.server.domain.entity.Category;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.LoginDto;
import photonet.server.webui.dto.UserDto;
import photonet.server.webui.dto.discover.PhotographerBasicDto;
import photonet.server.webui.profile.dto.ProfileBasicDto;
import photonet.server.webui.profile.dto.ProfileDto;

import java.util.List;
import java.util.Optional;

@Mapper(uses = {RateMapper.class, PhotoMapper.class, PostMapper.class, CommentMapper.class})
public interface UserMapper {

    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileBasicDto mapUserToBasicProfile(User user);

    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "rating", target = "rateCount")
    ProfileDto mapUserToProfileDto(User user);

    User mapUserDtoToUser(UserDto dto);

    LoginDto mapToLoginDto(User user);

    default String mapUserToString(User user) {
        return Optional.ofNullable(user).map(User::getUserName).orElse(null);
    }

    default UserDetails mapUserToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    @Mapping(source = "ratings", target = "rating")
    @Mapping(source = "ratings", target = "rateCount")
    @Mapping(target = "postsCount", source = "posts")
    PhotographerBasicDto mapToBasicProfile(User photographer);

    default Long countOpinion(List<Post> list) {
        return (long) list.size();
    }

    default String mapCategory(Category category) {
        return category.getName();
    }

    @Mapping(source = "ratings", target = "rating")
    @Mapping(source = "ratings", target = "rateCount")
    @Mapping(source = "posts", target = "postsCount")
    ProfileDto mapToDto(User source);

}
