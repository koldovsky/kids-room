package ua.softserveinc.tc.entity;


public class ReturnModel {
    private String email;

    private String message;

    public ReturnModel() {
    }

    public ReturnModel(String email) { this.email = email; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
