package photonet.server.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(){
        this.setMessage("Resource has not been found");
        this.setStatus(HttpStatus.NOT_FOUND);
    }

}
