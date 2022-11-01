package photonet.server.webui.profile.dto;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import photonet.server.webui.dto.CommentDto;
import photonet.server.webui.dto.discover.PhotographerBasicDto;
import photonet.server.webui.dto.post.PostSimpleDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends PhotographerBasicDto {

  private String bio;
  private String email;
  private List<PostSimpleDto> posts;
  private List<CommentDto> comments;

}

