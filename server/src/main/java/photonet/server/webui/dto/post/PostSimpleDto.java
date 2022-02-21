package photonet.server.webui.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSimpleDto {

    private Long id;
    private String photo;
    private Long likes;
    private String author;
    private LocalDateTime timestamp;

}
