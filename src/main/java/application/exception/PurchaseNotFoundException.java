package application.exception;

import javax.persistence.EntityNotFoundException;

/**
 * Created by kkurowska on 19.12.2016.
 */
public class PurchaseNotFoundException extends EntityNotFoundException {

    public PurchaseNotFoundException() {
    }

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
