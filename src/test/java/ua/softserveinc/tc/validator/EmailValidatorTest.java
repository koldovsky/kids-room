package ua.softserveinc.tc.validator;

/**
 * Created by Valery on 16.11.2016.
 * Created unit tests for email validation.
 * <p>
 * Некоторые люди, сталкиваясь с проблемой, думают: «О, я воспользуюсь регулярными выражениями».
 * Теперь у них две проблемы.
 * <p>
 * Некоторые люди, сталкиваясь с проблемой, думают: «О, я воспользуюсь регулярными выражениями».
 * Теперь у них две проблемы.
 */

/**
 * Некоторые люди, сталкиваясь с проблемой, думают: «О, я воспользуюсь регулярными выражениями».
 Теперь у них две проблемы.
 * */

import org.junit.Assert;
import org.junit.Test;
import ua.softserveinc.tc.constants.ValidationConstants;

import java.util.Arrays;
import java.util.List;

public class EmailValidatorTest {


    @Test
    public void testForContainingADogSymbol() {
        String incorrectEmailAddress = "mkyong";
        Assert.assertFalse("Email address should contain a '@' symbol.",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForExtraDotAfterDogSymbol() {
        String incorrectEmailAddress = "mkyong@.com.my";
        Assert.assertFalse("Email address shouldn't contain a dot after a '@' symbol.",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testEmailTldForNotContainingDigits() {
        String incorrectEmailAddress = "mkyong@gmail.com.1a";
        Assert.assertFalse("Email address shouldn't contain a digit in tld.",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForCorrectTld() {
        String incorrectEmailAddress = "mkyong123@gmail.a";
        Assert.assertFalse("Email address should contain at least two character in it tld",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testAllowSymbolsInEmail() {
        String incorrectEmailAddress = "mkyong()*@gmail.com";
        Assert.assertFalse("Email address should contain only character, digit, underscore and dash",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForDoubleDots() {
        String incorrectEmailAddress = "mkyong..2002@gmail.com";
        Assert.assertFalse("Email address shouldn't contain double dots",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForContainingValidEmailWithoutDot() {
        String incorrectEmailAddress = "mkyong.@gmail.com";
        Assert.assertFalse("Email address should contain only character, digit, underscore and dash",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForOneDogSymbol() {
        String incorrectEmailAddress = "mkyong@mkyong@gmail.com";
        Assert.assertFalse("Email address should contain at least two character in it tld",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForEmailNotStartingWithDot() {
        String incorrectEmailAddress = ".mkyong@mkyong.com";
        Assert.assertFalse("Email address shouldn't start with dot symbol",
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testValidEmail() {
        List<String> listOfEmails = Arrays.asList("mkyong@yahoo.com",
                "mkyong-100@yahoo.com", "mkyong.100@yahoo.com",
                "mkyong111@mkyong.com", "mkyong-100@mkyong.net",
                "mkyong.100@mkyong.com.au", "mkyong@1.com",
                "mkyong@gmail.com.com", "mkyong+100@gmail.com",
                "mkyong-100@yahoo-test.com", "alex@yandex.ru",
                "alex-27@yandex.com",
                "alex.27@yandex.com",
                "alex111@devcolibri.com",
                "alex.100@devcolibri.com.ua",
                "alex@1.com",
                "alex@gmail.com.com",
                "alex+27@gmail.com",
                "alex-27@yandex-test.com");

        listOfEmails.forEach(item -> Assert.assertEquals("This should be [ " + item + " ] valid email address",
                true,
                item.matches(ValidationConstants.SIMPLE_EMAIL_REGEX)));
    }

    @Test
    public void testInvalidEmail() {
        List<String> listOfEmails = Arrays.asList("mkyong", "mkyong@.com.my",
                "mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
                ".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
                "mkyong..2002@gmail.com", "mkyong.@gmail.com",
                "mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a", "devcolibri",
                "alex@.com.ua",
                "alex123@gmail.a",
                "alex123@.com",
                "alex123@.com.com",
                ".alex@devcolibri.com",
                "alex()*@gmail.com",
                "alex@%*.com",
                "alex..2013@gmail.com",
                "alex.@gmail.com",
                "alex@devcolibri@gmail.com",
                "alex@gmail.com.1ua");

        listOfEmails.forEach(item -> Assert.assertNotEquals("This should be [ " + item + " ] invalid email address",
                true,
                item.matches(ValidationConstants.SIMPLE_EMAIL_REGEX)));
    }

}

