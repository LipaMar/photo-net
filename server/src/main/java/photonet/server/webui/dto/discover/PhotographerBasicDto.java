package photonet.server.webui.dto.discover;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import photonet.server.webui.dto.profile.ProfileBasicDto;

@Data
public class PhotographerBasicDto extends ProfileBasicDto {

  private Long postsCount;
  private String city;
  private BigDecimal price;
  private List<String> categories;

}
