package application.exception;

import java.util.List;

/**
 * Created by kkurowska on 21.12.2016.
 */
public class ValidationException extends RuntimeException {

    private List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        super("ValidationException");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}