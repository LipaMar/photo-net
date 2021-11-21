package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.Comment;

@Mapper
public interface CommentMapper extends OpinionMapper<Comment> {
}
