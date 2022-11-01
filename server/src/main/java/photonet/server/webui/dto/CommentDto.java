package photonet.server.webui.dto;

import java.util.Date;
import lombok.Data;

@Data
public class CommentDto {

  private Long id;
  private String author;
  private String target;
  private String content;
  private Date added;
  private boolean anonymous;

}
