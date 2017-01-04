package application.service;

import application.exception.*;
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

    public String getMessageWrongDateFormat(){
        return message.getMessage(DATE, WRONG_FORMAT);
    }

    public String getMessageProductNotFound(){
        return message.getMessage(PRODUCT, NOT_FOUND);
    }

    public String getMessageStoreNotFound(){
        return message.getMessage(STORE, NOT_FOUND);
    }

    public String getMessagePurchaseNotFound(){
        return message.getMessage(PURCHASE, NOT_FOUND);
    }

    public String getMessageValidation(ValidationException e){
        String outputMessage = new String(e.getMessage() + "\n");
        List<ValidationError> errors = e.getErrors();
        for (int i = 0; i < errors.size(); i++) {
            ErrorField errorField = errors.get(i).getErrorField();
            ErrorDescription errorDescription = errors.get(i).getErrorDescription();
            outputMessage += message.getMessage(errorField, errorDescription) + "\n";
        }
        return outputMessage;
    }
}
