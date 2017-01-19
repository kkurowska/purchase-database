package application.exception;

import java.util.HashMap;
import java.util.Map;

import static application.exception.ErrorDescription.*;
import static application.exception.ErrorDescription.ALREADY_EXIST;
import static application.exception.ErrorField.*;
import static application.exception.ErrorField.DATE;
import static application.exception.ErrorField.PRICE;

/**
 * Created by kkurowska on 05.01.2017.
 */
public class PolishMessages implements Messages {

    private Map<ErrorField, String> mapField = new HashMap<ErrorField,String>() {{
        put(PRODUCT, "produkt");
        put(STORE, "sklep");
        put(PURCHASE, "zakup");
        put(ID, "id");
        put(NAME, "nazwa");
        put(PRODUCER, "producent");
        put(UNIT, "jednostka");
        put(CATEGORY, "kategoria");
        put(PRODUCT_ID, "id produktu");
        put(STORE_ID, "id sklepu");
        put(PRICE, "cena");
        put(DATE, "data");
        put(ACTION, "akcja");
        put(PASSWORD, "hasło");
        put(NEW_PASSWORD, "nowe hasło");
        put(OLD_PASSWORD, "stare hasło");
        put(USER_ROLE, "uprawnienia użytkownika"); //jakiś inny opis?
        put(USER, "użytkownik");
        put(ACCESS, "dostęp");
    }};

    private  Map<ErrorDescription, String> mapDescription = new HashMap<ErrorDescription,String>() {{
        put(NOT_FOUND, "- nie znaleziono");
        put(WRONG_FORMAT, "- zły format");
        put(WRONG, "- złe");
        put(MAY_NOT_BE_NULL, "- pole nie może być puste");
        put(NOT_ALLOWED, "- wartość nie dozwolona");
        put(ALREADY_EXIST, "- już istnieje");
        put(DENIED, "- odmowa");
    }};

    public String getMessage(ErrorField ef, ErrorDescription ed){
        return mapField.get(ef)+ " " + mapDescription.get(ed);
    }

    public PolishMessages() {
    }
}
