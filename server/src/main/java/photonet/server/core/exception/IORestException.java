package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class IORestException extends RestException {

    public IORestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
