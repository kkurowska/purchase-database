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
    }};

    private  Map<ErrorDescription, String> mapDescription = new HashMap<ErrorDescription,String>() {{
        put(NOT_FOUND, "not found");
        put(WRONG_FORMAT, "wrong format");
        put(MAY_NOT_BE_NULL, "may not be null");
        put(NOT_ALLOWED, "not allowed");
        put(ALREADY_EXIST, "already exist");
    }};

    public String getMessage(ErrorField ef, ErrorDescription ed){
        return mapField.get(ef)+ " " + mapDescription.get(ed);
    }

    public EnglishMessages() {
    }
}
