package lt.tazkazz.mrqtr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FavouriteArtistNotFoundException extends RuntimeException {
    public FavouriteArtistNotFoundException(String message) {
        super(message);
    }
}
