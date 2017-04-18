package ua.softserveinc.tc.server.exception;

import java.util.List;

public class UserInputException extends RuntimeException{

  private List<String> errorList;

  public UserInputException(List<String> list){
    errorList = list;
  }

  public List<String> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<String> errorList) {
    this.errorList = errorList;
  }
}
