package photonet.server.webui.profile.dto;

import lombok.Data;

@Data
public class ProfileBasicDto {

    private Boolean active;
    private String userName;
    private byte[] profilePicture;
    private Double rating;
    private Long rateCount;

}
