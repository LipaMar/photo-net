package photonet.server.webui.dto;

import lombok.Data;

@Data
public class UserInfoDto {

  private String userName;
  private String email;
  private boolean active;

}
