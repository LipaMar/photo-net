package photonet.server.domain.posts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.core.exception.ForbiddenRestException;
import photonet.server.core.utils.SecurityUtils;
import photonet.server.domain.entity.Follow;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.entity.Post;
import photonet.server.domain.mapper.PostMapper;
import photonet.server.domain.posts.repository.PostRepository;
import photonet.server.domain.service.FollowService;
import photonet.server.domain.service.UserService;
import photonet.server.domain.service.upload.FileService;
import photonet.server.webui.dto.post.PostSimpleDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final FollowService followService;
    @Qualifier("CloudService")
    private final FileService fileService;

    public List<PostSimpleDto> getPostsOfFollowedUsers() {
        final var user = userService.findByUserName(SecurityUtils.loggedUserName());
        final var allFollowed = followService.getAllFollowed(user.getUserName());
        List<Post> posts = new ArrayList<>();
        allFollowed.stream()
                   .map(Follow::getTarget)
                   .forEach(author -> posts.addAll(postRepository.findAllByAuthor(author)));
        return posts.stream().map(postMapper::mapToSimpleDto).collect(Collectors.toList());
    }

    @Transactional
    public void uploadNewPost(MultipartFile file) {
        final var path = fileService.saveFile(file);
        final var author = userService.findByUserName(SecurityUtils.loggedUserName());
        final var postToSave = new Post();
        postToSave.setAuthor(author);
        postToSave.setTimestamp(LocalDateTime.now());
        postToSave.setPhoto(userService.savePhoto(path));
        postRepository.save(postToSave);
    }

    @Transactional
    public void deletePost(Long id) {
        final var post = postRepository.findById(id).orElseThrow();
        isLoggedUserAuthor(post.getAuthor().getUserName(), SecurityUtils.loggedUserName());
        postRepository.deleteById(id);
    }

    private void isLoggedUserAuthor(String author, String loggedUser) {
        if(!author.equals(loggedUser)){
            throw new ForbiddenRestException();
        }
    }
}
