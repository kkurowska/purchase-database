package application.exception;

/**
 * Created by kkurowska on 05.01.2017.
 */
public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
