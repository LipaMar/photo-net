package photonet.server.domain.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import photonet.server.domain.entity.Comment;
import photonet.server.domain.entity.User;
import photonet.server.webui.dto.CommentDto;

import java.util.Optional;

@Mapper
public interface CommentMapper extends OpinionMapper<Comment> {

  CommentDto entityToDto(Comment comment);

  default String mapUserToString(User user) {
    return Optional.ofNullable(user).map(User::getUserName).orElse(null);
  }

  @Mapping(target = "author", ignore = true)
  @Mapping(target = "target", ignore = true)
  Comment dtoToEntity(CommentDto commentDto);

  @AfterMapping
  default void changeAuthorIfAnonymous(@MappingTarget CommentDto dto, Comment comment) {
    if (comment.isAnonymous()) {
      dto.setAuthor("Anonim");
    }
  }

}
