package photonet.server.webui.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends ProfileBasicDto {

    private String bio;
    private List<PostSimpleDto> posts;

}

