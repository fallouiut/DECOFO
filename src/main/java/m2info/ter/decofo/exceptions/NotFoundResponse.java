package m2info.ter.decofo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "page not found")
public class NotFoundResponse extends RuntimeException {
}