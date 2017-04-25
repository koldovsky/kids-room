package ua.softserveinc.tc.entity;


public class ResponseWithErrors {
    private String email;

    private String message;

    public ResponseWithErrors() {
    }

    public ResponseWithErrors(String email) { this.email = email; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public boolean setMessage(String message) {
        this.message = message;
        return true;
    }
}
