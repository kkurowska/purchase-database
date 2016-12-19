package application.exception;

import javax.persistence.EntityExistsException;

/**
 * Created by kkurowska on 19.12.2016.
 */
public class StoreExistException extends EntityExistsException {

    public StoreExistException() {
    }

    public StoreExistException(String message) {
        super(message);
    }
}
