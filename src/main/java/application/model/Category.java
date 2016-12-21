package application.model;


/**
 * Created by kkurowska on 15.12.2016.
 */

public enum Category {
    GROCERIES("GROCERIES"),
    COSMETICS("COSMETICS"),
    FASHION("FASHION"),
    PHARMACY("PHARMACY"),
    HOME("HOME"),
    OFFICE("OFFICE"),
    HOBBY("HOBBY");

    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final Category fromCategoryValue(String key) {
        for (Category c : Category.values()) {
            if (c.value.equals(key)) {
                return c;
            }
        }
        return null;
    }

}
