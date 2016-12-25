package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import static org.mockito.Mockito.when;

public class ManagerValidatorTest {

    private User user;

    private Errors errors;

    @InjectMocks
    private UserValidatorImpl userValidator;

    @Mock
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        errors = new BindException(user, "user");
        user.setEmail("myemail@gmail.com");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPhoneNumber("+380969880833");
    }

    @Test
    public void passCorrectDataExpectValidate() {
        userValidator.validateManager(user, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void passEmptyFirstNameExpectNotValidate() {
        user.setFirstName("");
        userValidator.validateManager(user, errors);
        Assert.assertTrue("The first name shouldn`t be empty",
                errors.hasErrors());
        Assert.assertEquals("Error first name message is incorrect",
                ValidationConstants.EMPTY_FIELD_MSG,
                errors.getFieldError(ValidationConstants.FIRST_NAME).getCode());
    }

    @Test
    public void passEmptyLastNameExpectNotValidate() {
        user.setLastName("");
        userValidator.validateManager(user, errors);
        Assert.assertTrue("The last name shouldn`t be empty", errors.hasErrors());
        Assert.assertEquals("Error last name message is incorrect",
                ValidationConstants.EMPTY_FIELD_MSG,
                errors.getFieldError(ValidationConstants.LAST_NAME).getCode());
    }

    @Test
    public void passIncorrectFirstNameExpectNotValidate() {
        user.setFirstName("inval1dFirstn@me");
        userValidator.validateManager(user, errors);
        Assert.assertTrue("First name shouldn`t cointain any fobidden symbols",
                errors.hasErrors());
        Assert.assertEquals("First name error message is incorrect",
                ValidationConstants.ADMINISTRATOR_INCORRECT_FIRST_NAME,
                errors.getFieldError(ValidationConstants.FIRST_NAME).getCode());
    }

    @Test
    public void passIncorrectLastNameExpectNotValidate() {
        user.setLastName("inval1dLastn@me");
        userValidator.validateManager(user, errors);
        Assert.assertTrue("Last name shouldn`t cointain any fobidden symbols",
                errors.hasErrors());
        Assert.assertEquals("Last name error message is incorrect",
                ValidationConstants.ADMINISTRATOR_INCORRECT_SECOND_NAME,
                errors.getFieldError(ValidationConstants.LAST_NAME).getCode());
    }

    @Test
    public void passIncorrectPhoneExcpectNotValidate() {
        user.setPhoneNumber("+3incorrectphone,");
        userValidator.validateManager(user, errors);
        Assert.assertTrue("Invalid phone format", errors.hasErrors());
        Assert.assertEquals("Incorrect phone error message",
                ValidationConstants.ADMINISTRATOR_INCORRECT_PHONE,
                errors.getFieldError(ValidationConstants.PHONE_NUMBER).getCode());
    }

    @Test
    public void passIncorrectEmailExpectNotValidate() {
        user.setEmail("incrorrect@mail.@mail,");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(new User());
        userValidator.validateManagerEmail(user, errors);
        Assert.assertTrue("Invalid email", errors.hasErrors());
        Assert.assertEquals("Incorrect email error message",
                ValidationConstants.EMAIL_NOT_VALID,
                errors.getFieldError(ValidationConstants.EMAIL).getCode());
    }
}

