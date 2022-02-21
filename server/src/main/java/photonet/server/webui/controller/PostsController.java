package photonet.server.webui.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Endpoints;
import photonet.server.domain.posts.service.PostService;
import photonet.server.webui.dto.post.PostSimpleDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.POST)
public class PostsController {

    private final PostService postService;

    @PostMapping
    public void uploadNewPost(MultipartFile file) {
        postService.uploadNewPost(file);
    }

    @DeleteMapping
    public void deletePost(@RequestParam Long id) {
        postService.deletePost(id);
    }

    @GetMapping
    public List<PostSimpleDto> getPostsOfFollowedUsers() {
        return postService.getPostsOfFollowedUsers();
    }

}
