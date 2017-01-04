package application.exception;

/**
 * Created by kkurowska on 20.12.2016.
 */
public class ValidationError {

    private ErrorField errorField;
    private ErrorDescription errorDescription;

    public ValidationError() {
    }

    public ValidationError(ErrorField errorField, ErrorDescription errorDescription) {
        this.errorField = errorField;
        this.errorDescription = errorDescription;
    }

    public ErrorField getErrorField() {
        return errorField;
    }

    public void setErrorField(ErrorField errorField) {
        this.errorField = errorField;
    }

    public ErrorDescription getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(ErrorDescription errorDescription) {
        this.errorDescription = errorDescription;
    }
}