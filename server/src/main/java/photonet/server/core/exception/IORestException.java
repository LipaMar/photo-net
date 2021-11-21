package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class IORestException extends RestException {

    public IORestException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public IORestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public IORestException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public IORestException(Throwable cause, HttpStatus httpStatus) {
        super(cause, httpStatus);
    }

    public IORestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace, httpStatus);
    }

}
