package photonet.server.webui.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import photonet.server.core.exception.AlreadyExistRestException;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler({AlreadyExistRestException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage alreadyExists(AlreadyExistRestException e) {
        return ErrorMessage.builder().status(e.getHttpStatus()).message(e.getMessage()).build();
    }

}
