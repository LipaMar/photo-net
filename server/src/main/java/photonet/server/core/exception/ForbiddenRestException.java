package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenRestException extends RestException {
    public ForbiddenRestException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenRestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
