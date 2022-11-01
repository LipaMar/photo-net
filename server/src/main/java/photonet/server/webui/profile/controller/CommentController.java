package photonet.server.webui.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import photonet.server.config.Endpoints;
import photonet.server.domain.comments.service.CommentService;
import photonet.server.webui.dto.CommentDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.COMMENT)
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public void addComment(@RequestBody CommentDto dto) {
    commentService.addComment(dto);
  }

  @DeleteMapping
  public void deleteComment(long id) {
    commentService.deleteComment(id);
  }

  @PutMapping
  public void editComment(CommentDto dto) {
    commentService.editComment(dto);
  }

}
