package application.exception;

import javax.persistence.EntityExistsException;

/**
 * Created by kkurowska on 19.12.2016.
 */
public class ProductExistException extends EntityExistsException {

    public ProductExistException() {
    }

    public ProductExistException(String message) {
        super(message);
    }
}
