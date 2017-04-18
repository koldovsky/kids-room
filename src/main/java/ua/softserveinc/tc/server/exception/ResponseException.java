package ua.softserveinc.tc.server.exception;

import java.util.List;

public class ResponseException {

  private String message;

  private List<String> userInputErrors;

  public ResponseException(String message) {
    this.message = message;
  }

  public ResponseException(List<String> userInputErrors) {
    this.userInputErrors = userInputErrors;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getUserInputErrors() {
    return userInputErrors;
  }

  public void setUserInputErrors(List<String> userInputErrors) {
    this.userInputErrors = userInputErrors;
  }
}
