package photonet.server.webui.dto;

import lombok.Data;

@Data
public class ProfilesBasicDto {

    private String userName;
    private String profilePicture;
    private double rating;

}
