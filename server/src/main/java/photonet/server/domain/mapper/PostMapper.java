package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.entity.Post;
import photonet.server.webui.dto.PostSimpleDto;

@Mapper(uses = {CommentMapper.class, LikeMapper.class, PhotoMapper.class})
public interface PostMapper {

    @Mapping(source = "photo", target = "photo")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "author.userName", target = "author")
    @Mapping(source = "likes", target = "likes")
    PostSimpleDto mapToSimpleDto(Post post);

}
