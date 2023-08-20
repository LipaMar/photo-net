package photonet.server.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    protected RestException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    protected RestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
