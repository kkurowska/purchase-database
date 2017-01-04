package application.exception;

/**
 * Created by kkurowska on 20.12.2016.
 */
public enum ErrorDescription {
    NOT_FOUND,
    WRONG_FORMAT,
    MAY_NOT_BE_NULL,
    NOT_ALLOWED,
    ALREADY_EXIST;

    private ErrorDescription() {
    }
}
