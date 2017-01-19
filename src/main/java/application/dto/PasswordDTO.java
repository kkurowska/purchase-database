package application.dto;

/**
 * Created by kkurowska on 19.01.2017.
 */
public class PasswordDTO {

    private String name;
    private String oldPassword;
    private String newPassword;

    public PasswordDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
