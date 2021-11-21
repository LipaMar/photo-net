package photonet.server.webui.dto;

import lombok.Data;

@Data
public class PostSimpleDto {

    private Long id;
    private byte[] photo;
    private Long comments;
    private Long likes;
    private String author;

}
