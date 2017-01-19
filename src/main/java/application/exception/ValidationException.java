package application.exception;

import java.util.List;

/**
 * Created by kkurowska on 21.12.2016.
 */
public class ValidationException extends RuntimeException {

    private List<Error> errors;

    public ValidationException(List<Error> errors) {
        super("ValidationException");
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}