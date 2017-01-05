package application.controller;

import application.exception.*;
import application.service.GlobalExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;

/**
 * Created by kkurowska on 21.12.2016.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    GlobalExceptionService globalExceptionService;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public String handleException(ValidationException e) {
        return globalExceptionService.getMessageValidation(e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WrongDateFormatException.class)
    public String handleException(WrongDateFormatException e) {
        return globalExceptionService.getMessageWrongDateFormat();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProductNotFoundException.class)
    public String handleException(ProductNotFoundException e) {
        return globalExceptionService.getMessageProductNotFound();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = StoreNotFoundException.class)
    public String handleException(StoreNotFoundException e) {
        return globalExceptionService.getMessageStoreNotFound();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = PurchaseNotFoundException.class)
    public String handleException(PurchaseNotFoundException e) {
        return globalExceptionService.getMessagePurchaseNotFound();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ActionNotAllowedException.class)
    public String handleException(ActionNotAllowedException e) {
        return globalExceptionService.getMessageActionNotAllowed();
    }
}

