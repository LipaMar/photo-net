package photonet.server.webui.profile.dto;

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
