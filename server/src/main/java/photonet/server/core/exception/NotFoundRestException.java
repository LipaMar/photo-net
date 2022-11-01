package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundRestException extends RestException {

  public NotFoundRestException() {
    super(HttpStatus.NOT_FOUND);
  }

  public NotFoundRestException(HttpStatus httpStatus) {
    super(httpStatus);
  }

  public NotFoundRestException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }

  public NotFoundRestException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause, httpStatus);
  }

  public NotFoundRestException(Throwable cause, HttpStatus httpStatus) {
    super(cause, httpStatus);
  }

  public NotFoundRestException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, HttpStatus httpStatus) {
    super(message, cause, enableSuppression, writableStackTrace, httpStatus);
  }

}

