package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistRestException extends RestException{

    public AlreadyExistRestException(){super(HttpStatus.CONFLICT);}
    public AlreadyExistRestException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public AlreadyExistRestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public AlreadyExistRestException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public AlreadyExistRestException(Throwable cause, HttpStatus httpStatus) {
        super(cause, httpStatus);
    }

    public AlreadyExistRestException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
    }

}

