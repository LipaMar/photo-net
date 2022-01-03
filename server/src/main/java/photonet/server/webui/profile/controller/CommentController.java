package photonet.server.webui.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import photonet.server.config.Endpoints;
import photonet.server.domain.profile.service.CommentService;
import photonet.server.webui.dto.CommentDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.COMMENT)
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public void addComment(CommentDto dto) {
        commentService.addComment(dto);
    }

    @DeleteMapping()
    public void deleteComment(long id) {
        commentService.deleteComment(id);
    }

    @PutMapping()
    public void editComment(CommentDto dto) {
        commentService.editComment(dto);
    }

}
