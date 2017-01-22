package ua.softserveinc.tc.server.exception;


public class BadUploadException extends RuntimeException {
    private String reason = "Bad upload";

    public BadUploadException() {
    }

    public BadUploadException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
