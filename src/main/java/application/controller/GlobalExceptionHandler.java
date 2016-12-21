package application.controller;

import application.exception.ValidationError;
import application.exception.ValidationException;
import application.exception.WrongDateFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by kkurowska on 21.12.2016.
 */

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public String handleException(ValidationException e) {
        String message = new String(e.getMessage() + "\n");
        List<ValidationError> errors = e.getErrors();
        for (int i = 0; i < errors.size() ; i++) {
            message += errors.get(i).toString() + "\n";
        }
        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WrongDateFormatException.class)
    public String handleException(WrongDateFormatException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public String handleException(EntityNotFoundException e) {
        return e.getMessage();
    }
}

