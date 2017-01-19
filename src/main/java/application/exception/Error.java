package application.exception;

/**
 * Created by kkurowska on 20.12.2016.
 */
public class Error {

    private ErrorField errorField;
    private ErrorDescription errorDescription;

    public Error() {
    }

    public Error(ErrorField errorField, ErrorDescription errorDescription) {
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