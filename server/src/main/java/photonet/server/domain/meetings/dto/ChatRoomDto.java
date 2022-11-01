package photonet.server.domain.meetings.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChatRoomDto {

  private Long id;
  private String sender;
  private String recipient;
  private List<MessageDto> messages;

}
