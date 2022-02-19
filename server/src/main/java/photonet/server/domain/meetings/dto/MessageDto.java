package photonet.server.domain.meetings.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {

    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long chatRoomId;
    private String author;

}
