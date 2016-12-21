package application.exception;

/**
 * Created by kkurowska on 20.12.2016.
 */
public enum  ErrorMessages {
    MAY_NOT_BE_NULL("may not be null"),
    NOT_ALLOWED("not allowed"),
    ALREADY_EXIST("already exist"),
    NOT_FOUND("not found");

    private String message;

    private ErrorMessages(String s) {
        message = s;
    }
}
