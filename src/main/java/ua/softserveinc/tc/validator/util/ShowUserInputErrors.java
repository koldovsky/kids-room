package ua.softserveinc.tc.validator.util;


import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import ua.softserveinc.tc.server.exception.UserInputException;

public final class ShowUserInputErrors {

  private ShowUserInputErrors(){}

  public static <T,V extends Validator> void validateDTO (T dto,BindingResult bindingResult,
      MessageSource messageSource, V validator){
    validator.validate(dto, bindingResult);
    List<String> listError = new ArrayList<String>();
    if (bindingResult.hasErrors()) {
      for (Object object : bindingResult.getAllErrors()) {
        FieldError er = (FieldError) object;
        listError
            .add(messageSource.getMessage(er.getCode(), null, LocaleContextHolder.getLocale()));
      }
      throw new UserInputException(listError);
    }

  }
}
