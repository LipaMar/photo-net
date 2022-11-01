package photonet.server.domain.meetings.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageDto {

  private Long id;
  private String content;
  private LocalDateTime timestamp;
  private Long chatRoomId;
  private String author;

}
