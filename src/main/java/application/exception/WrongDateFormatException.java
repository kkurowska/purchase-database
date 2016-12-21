package application.exception;

import java.text.ParseException;

/**
 * Created by kkurowska on 21.12.2016.
 */
public class WrongDateFormatException extends IllegalArgumentException {

    public WrongDateFormatException() {
    }

    public WrongDateFormatException(String s) {
        super(s);
    }
}
