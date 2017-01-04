package application.utils;

/**
 * Created by kkurowska on 04.01.2017.
 */
public enum MyDateFormat {
    MY_DATE_FORMAT("yyyy/MM/dd HH:mm:ss");

    private String value;

    MyDateFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
