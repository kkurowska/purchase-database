package application.exception;

import javax.persistence.EntityNotFoundException;

/**
 * Created by kkurowska on 19.12.2016.
 */
public class StoreNotFoundException extends EntityNotFoundException {

    public StoreNotFoundException() {
    }

    public StoreNotFoundException(String message) {
        super(message);
    }
}
