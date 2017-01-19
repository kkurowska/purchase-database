package application.exception;

/**
 * Created by kkurowska on 19.01.2017.
 */
public class MyRuntimeException extends RuntimeException {

    private Error error;

    public MyRuntimeException(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
