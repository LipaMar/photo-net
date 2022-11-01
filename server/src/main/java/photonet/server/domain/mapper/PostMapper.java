package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.entity.Post;
import photonet.server.webui.dto.post.PostSimpleDto;

@Mapper(uses = {CommentMapper.class, LikeMapper.class, PhotoMapper.class})
public interface PostMapper {

  @Mapping(source = "author.userName", target = "author")
  PostSimpleDto mapToSimpleDto(Post post);

}
