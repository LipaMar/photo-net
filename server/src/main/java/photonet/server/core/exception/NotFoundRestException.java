package photonet.server.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundRestException extends RestException {

    public NotFoundRestException() {
        super(HttpStatus.NOT_FOUND);
    }

}
