package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.entity.Category;
import photonet.server.domain.entity.Photographer;
import photonet.server.domain.entity.Post;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

import java.util.List;

@Mapper(uses = {RateMapper.class, PhotoMapper.class, PostMapper.class})
public interface PhotographerMapper {

    @Mapping(source = "user.active", target = "active")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.profilePicture", target = "profilePicture")
    @Mapping(source = "user.ratings", target = "rating")
    @Mapping(source = "user.ratings", target = "rateCount")
    @Mapping(target = "postsCount", source = "posts")
    PhotographerBasicDto mapToBasicProfile(Photographer photographer);

    default Long countOpinion(List<Post> list) {
        return (long) list.size();
    }
    default String mapCategory(Category category){
        return category.getName();
    };



}
