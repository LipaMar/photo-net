package photonet.server.webui.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import photonet.server.domain.entity.Comment;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.Rate;

import javax.persistence.*;
import java.util.List;

@Data
public class ProfileDto extends ProfileBasicDto {

    private String bio;

}
