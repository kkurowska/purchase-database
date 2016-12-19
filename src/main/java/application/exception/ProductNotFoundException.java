package application.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 * Created by kkurowska on 19.12.2016.
 */
public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
