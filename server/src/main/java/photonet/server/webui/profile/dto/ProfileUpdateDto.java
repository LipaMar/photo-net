package photonet.server.webui.profile.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ProfileUpdateDto {

  private String userName;
  private Boolean isPublic;
  private String bio;
  private String city;
  private BigDecimal price;
  private List<String> categories;

}
