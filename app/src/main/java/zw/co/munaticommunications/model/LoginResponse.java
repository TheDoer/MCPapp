//Author: Adonis Rumbwere

package zw.co.munaticommunications.model;



public class LoginResponse {
    private boolean error;
    private String message;
    private User user;

    public LoginResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
        this.user = new User();
    }

    public LoginResponse(boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
