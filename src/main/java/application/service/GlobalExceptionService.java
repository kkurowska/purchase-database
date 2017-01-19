package application.service;

import application.exception.*;
import application.exception.Error;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;

/**
 * Created by kkurowska on 04.01.2017.
 */

@Service
public class GlobalExceptionService {

    private Messages message = new EnglishMessages();

    public String getMessageActionNotAllowed(){
        return message.getMessage(ACTION, NOT_ALLOWED);
    }

    public String getMessageValidation(ValidationException e){
        String outputMessage = new String("");
        List<Error> errors = e.getErrors();
        for (int i = 0; i < errors.size(); i++) {
            ErrorField errorField = errors.get(i).getErrorField();
            ErrorDescription errorDescription = errors.get(i).getErrorDescription();
            outputMessage += message.getMessage(errorField, errorDescription) + "\n";
        }
        return outputMessage;
    }

    public String getMessageAccessDenied() {
        return message.getMessage(ACCESS, DENIED);
    }

    public String getMessageMyRuntime(MyRuntimeException e) {
        return message.getMessage(e.getError().getErrorField(), e.getError().getErrorDescription());
    }
}
