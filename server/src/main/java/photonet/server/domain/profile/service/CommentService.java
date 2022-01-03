package photonet.server.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import photonet.server.domain.mapper.CommentMapper;
import photonet.server.domain.profile.repository.CommentRepository;
import photonet.server.webui.dto.CommentDto;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public void addComment(CommentDto dto) {
        commentRepository.save(commentMapper.dtoToEntity(dto));
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public void editComment(CommentDto dto) {
        addComment(dto);
    }
}
