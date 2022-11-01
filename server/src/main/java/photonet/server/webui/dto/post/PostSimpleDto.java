package photonet.server.webui.dto.post;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostSimpleDto {

  private Long id;
  private String photo;
  private Long likes;
  private String author;
  private LocalDateTime timestamp;

}
