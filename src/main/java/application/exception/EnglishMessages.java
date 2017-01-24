package application.exception;

import java.util.HashMap;
import java.util.Map;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorField.*;

/**
 * Created by kkurowska on 04.01.2017.
 */


public class EnglishMessages implements Messages {
    private  Map<ErrorField, String> mapField = new HashMap<ErrorField,String>() {{
        put(PRODUCT, "product");
        put(STORE, "store");
        put(PURCHASE, "purchase");
        put(ID, "id");
        put(NAME, "name");
        put(PRODUCER, "producer");
        put(UNIT, "unit");
        put(CATEGORY, "category");
        put(PRODUCT_ID, "product id");
        put(STORE_ID, "store id");
        put(PRICE, "price");
        put(DATE, "date");
        put(ACTION, "action");
        put(PASSWORD, "password");
        put(NEW_PASSWORD, "new password");
        put(OLD_PASSWORD, "old password");
        put(USER_ROLE, "user role");
        put(USER, "user");
        put(ACCESS, "access");
    }};

    private  Map<ErrorDescription, String> mapDescription = new HashMap<ErrorDescription,String>() {{
        put(NOT_FOUND, "not found");
        put(WRONG_FORMAT, "wrong format");
        put(WRONG, "wrong");
        put(MAY_NOT_BE_NULL, "may not be null");
        put(NOT_ALLOWED, "not allowed");
        put(ALREADY_EXIST, "already exist");
        put(DENIED, "denied");
        put(DELETED, "deleted");
        put(CHANGED, "changed");
    }};

    public String getMessage(ErrorField ef, ErrorDescription ed){
        return mapField.get(ef)+ " " + mapDescription.get(ed);
    }

    public EnglishMessages() {
    }
}
