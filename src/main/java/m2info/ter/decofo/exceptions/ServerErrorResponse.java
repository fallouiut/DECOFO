package m2info.ter.decofo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "video not found")
public class ServerErrorResponse extends RuntimeException {
    public ServerErrorResponse(String s) {
        super(s);
    }
}
