package ua.softserveinc.tc.validator;

/**
 * Created by Valery on 16.11.2016.
 */
import org.junit.Assert;
import org.junit.Test;
import ua.softserveinc.tc.constants.ValidationConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailValidatorTest {


    @Test
    public void testForContainingADogSymbol() {
        String incorrectEmailAddress = "mkyong";
        Assert.assertEquals("Email address should contain a '@' symbol.",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForExtraDotAfterDogSymbol() {
        String incorrectEmailAddress = "mkyong@.com.my";
        Assert.assertEquals("Email address shouldn't contain a dot after a '@' symbol.",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testEmailTldForNotContainingDigits() {
        String incorrectEmailAddress = "mkyong@gmail.com.1a";
        Assert.assertEquals("Email address shouldn't contain a digit in tld.",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForCorrectTld() {
        String incorrectEmailAddress = "mkyong123@gmail.a";
        Assert.assertEquals("Email address should contain at least two character in it tld",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testAllowSymbolsInEmail() {
        String incorrectEmailAddress = "mkyong()*@gmail.com";
        Assert.assertEquals("Email address should contain only character, digit, underscore and dash",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForDoubleDots() {
        String incorrectEmailAddress = "mkyong..2002@gmail.com";
        Assert.assertEquals("Email address shouldn't contain double dots",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForContainingValidEmailWithoutDot() {
        String incorrectEmailAddress = "mkyong.@gmail.com";
        Assert.assertEquals("Email address should contain only character, digit, underscore and dash",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForOneDogSymbol() {
        String incorrectEmailAddress = "mkyong@mkyong@gmail.com";
        Assert.assertEquals("Email address should contain at least two character in it tld",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testForEmailNotStartingWithDot() {
        String incorrectEmailAddress = ".mkyong@mkyong.com";
        Assert.assertEquals("Email address shouldn't start with dot symbol",
                false,
                incorrectEmailAddress.matches(ValidationConstants.SIMPLE_EMAIL_REGEX));
    }

    @Test
    public void testValidEmail() {
        List<String> listOfEmails = new ArrayList<>();
        listOfEmails.addAll(Arrays.asList("mkyong@yahoo.com",
                "mkyong-100@yahoo.com", "mkyong.100@yahoo.com",
                "mkyong111@mkyong.com", "mkyong-100@mkyong.net",
                "mkyong.100@mkyong.com.au", "mkyong@1.com",
                "mkyong@gmail.com.com", "mkyong+100@gmail.com",
                "mkyong-100@yahoo-test.com"));

        listOfEmails.forEach(item -> Assert.assertEquals("This should be [ " + item + " ] valid email address",
                true,
                item.matches(ValidationConstants.SIMPLE_EMAIL_REGEX)));
    }

    @Test
    public void testInvalidEmail() {
        List<String> listOfEmails = new ArrayList<>();
        listOfEmails.addAll(Arrays.asList("mkyong", "mkyong@.com.my",
                "mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
                ".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
                "mkyong..2002@gmail.com", "mkyong.@gmail.com",
                "mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a"));

        listOfEmails.forEach(item -> Assert.assertNotEquals("This should be [ " + item + " ] invalid email address",
                true,
                item.matches(ValidationConstants.SIMPLE_EMAIL_REGEX)));
    }
}

