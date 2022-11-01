package photonet.server.webui.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.config.Endpoints;
import photonet.server.domain.posts.service.PostService;
import photonet.server.webui.dto.post.PostSimpleDto;

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

  @PostMapping(Endpoints.LIKE)
  public void like(@RequestParam Long id) {
    postService.likePost(id);
  }

  @GetMapping(Endpoints.LIKE)
  public List<Long> getLiked() {
    return postService.getLikedPosts();
  }


}
