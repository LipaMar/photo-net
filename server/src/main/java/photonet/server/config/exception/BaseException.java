package photonet.server.config.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException {

    private String message;
    private HttpStatus status;

}
