package photonet.server.webui.dto;

import lombok.Data;

@Data
public class ProfileBasicDto {

    private Boolean active;
    private String userName;
    private String profilePicture;
    private double rating;
    private int rateCount;

}
