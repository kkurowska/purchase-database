package application.controller;

import application.exception.*;
import application.service.GlobalExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String handleValidationException(ValidationException e) {
        return globalExceptionService.getMessageValidation(e);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ActionNotAllowedException.class)
    public String handleActionNotAllowedException(ActionNotAllowedException e) {
        return globalExceptionService.getMessageActionNotAllowed();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e) {
        return globalExceptionService.getMessageAccessDenied();
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyRuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> handleMyRuntimeException(MyRuntimeException e) {
        return new ResponseEntity<String>(globalExceptionService.getMessageMyRuntime(e), HttpStatus.BAD_REQUEST);
    }

    public GlobalExceptionHandler(GlobalExceptionService globalExceptionService) {
        this.globalExceptionService = globalExceptionService;
    }
}

