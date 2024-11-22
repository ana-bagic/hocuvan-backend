package fer.proinz.hocuvan.helpers;

public class UserFilterDTO {
    private String username;

    public UserFilterDTO() {
    }

    public UserFilterDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
