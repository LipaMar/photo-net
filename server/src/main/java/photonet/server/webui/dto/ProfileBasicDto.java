package photonet.server.webui.dto;

import lombok.Data;

@Data
public class ProfileBasicDto {

    private String userName;
    private String profilePicture;
    private double rating;
    private int rateCount;

}
