package ua.softserveinc.tc.server.exception;

public class NoSuchRowException extends RuntimeException{

  private String reason;

  public NoSuchRowException(){}

  public NoSuchRowException(String reason){
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
