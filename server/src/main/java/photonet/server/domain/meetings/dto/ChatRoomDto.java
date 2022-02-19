package photonet.server.domain.meetings.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomDto {

    private Long id;
    private String sender;
    private String recipient;
    private List<MessageDto> messages;

}
