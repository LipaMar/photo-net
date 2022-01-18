package photonet.server.webui.profile.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProfileUpdateDto {

    private String userName;
    private String bio;
    private String city;
    private BigDecimal price;
    private List<String> categories;

}
