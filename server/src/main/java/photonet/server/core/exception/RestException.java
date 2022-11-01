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

  protected RestException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause);
    this.httpStatus = httpStatus;
  }

  protected RestException(Throwable cause, HttpStatus httpStatus) {
    super(cause);
    this.httpStatus = httpStatus;
  }

  protected RestException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace,
      HttpStatus httpStatus) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.httpStatus = httpStatus;
  }

}
