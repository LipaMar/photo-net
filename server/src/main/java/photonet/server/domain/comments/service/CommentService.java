package photonet.server.domain.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.core.exception.NotFoundRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.mapper.CommentMapper;
import photonet.server.domain.comments.repository.CommentRepository;
import photonet.server.domain.repository.UserRepository;
import photonet.server.webui.dto.CommentDto;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void addComment(CommentDto dto) {
        var entity = commentMapper.dtoToEntity(dto);
        var author = userRepository.findByUserName(SecurityUtils.loggedUserName()).orElseThrow(NotFoundRestException::new);
        var target = userRepository.findByUserName(dto.getTarget()).orElseThrow(NotFoundRestException::new);
        entity.setAuthor(author);
        entity.setTarget(target);
        entity.setAdded(new Date());
        commentRepository.save(entity);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public void editComment(CommentDto dto) {
        addComment(dto);
    }
}
