package application.model;

/**
 * Created by kkurowska on 20.12.2016.
 */
public enum Unit {
    KG("KG"),
    LITER("LITER"),
    PIECE("PIECE");

    private String value;

    Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final Unit fromUnitValue(String key) {
        for (Unit u : Unit.values()) {
            if (u.value.equals(key)) {
                return u;
            }
        }
        return null;
    }
}
