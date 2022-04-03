package photonet.server.webui.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {

    private String userName;
    private Boolean active;
    private String role;

}
