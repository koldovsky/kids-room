package ua.softserveinc.tc.server.exception;

/**
 * Created by Nestor on 11.06.2016.
 */
public class BadUploadException extends RuntimeException{
    private String reason = "Bad upload";

    public BadUploadException(){}

    public BadUploadException(String reason){
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
