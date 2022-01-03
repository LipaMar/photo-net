package photonet.server.webui.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private long id;
    private String author;
    private String target;
    private String content;
    private Date added;
    private boolean anonymous;

}
