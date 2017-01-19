package application.utils;

/**
 * Created by kkurowska on 19.01.2017.
 */
public enum UserRoles {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final UserRoles fromUserRolesValue(String key) {
        for (UserRoles u : UserRoles.values()) {
            if (u.value.equals(key)) {
                return u;
            }
        }
        return null;
    }
}
