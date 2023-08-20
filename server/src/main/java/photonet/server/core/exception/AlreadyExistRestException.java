package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistRestException extends RestException {

    public AlreadyExistRestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}

