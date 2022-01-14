package photonet.server.webui.profile.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import photonet.server.webui.dto.CommentDto;
import photonet.server.webui.dto.PostSimpleDto;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends PhotographerBasicDto {

    private String bio;
    private List<PostSimpleDto> posts;
    private List<CommentDto> comments;

}
