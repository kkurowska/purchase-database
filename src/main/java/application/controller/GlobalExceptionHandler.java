package application.controller;

import application.exception.*;
import application.service.GlobalExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ActionNotAllowedException.class)
    public String handleException(ActionNotAllowedException e) {
        return globalExceptionService.getMessageActionNotAllowed();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleException(AccessDeniedException e) {
        return globalExceptionService.getMessageAccessDenied();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyRuntimeException.class)
    public String RunTimeException(MyRuntimeException e) {
        return globalExceptionService.getMessageMyRuntime(e);
    }
}

