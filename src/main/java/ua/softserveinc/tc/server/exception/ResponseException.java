package ua.softserveinc.tc.server.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
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
