package photonet.server.webui.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import photonet.server.core.exception.AlreadyExistRestException;
import photonet.server.core.exception.ForbiddenRestException;

@RestControllerAdvice
public class RestExceptionAdvice {

  @ExceptionHandler({AlreadyExistRestException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage alreadyExists(AlreadyExistRestException e) {
    return ErrorMessage.builder().status(e.getHttpStatus()).message(e.getMessage()).build();
  }

  @ExceptionHandler({ForbiddenRestException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage alreadyExists(ForbiddenRestException e) {
    return ErrorMessage.builder().status(e.getHttpStatus()).message(e.getMessage()).build();
  }

}
