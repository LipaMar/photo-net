package photonet.server.webui.dto.profile;

import lombok.Data;

@Data
public class ProfileBasicDto {

  private Boolean active;
  private Boolean isPublic;
  private String userName;
  private String profilePicture;
  private Double rating;
  private Long rateCount;
  private Long observers;

}
