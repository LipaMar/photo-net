package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import photonet.server.domain.entity.Comment;
import photonet.server.webui.dto.CommentDto;

@Mapper
public interface CommentMapper extends OpinionMapper<Comment> {

    CommentDto entityToDto(Comment comment);

    Comment dtoToEntity(CommentDto commentDto);

}
