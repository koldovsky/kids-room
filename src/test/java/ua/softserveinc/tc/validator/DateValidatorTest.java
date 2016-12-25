package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class DateValidatorTest {

    private TimeValidator timeValidator;

    @Before
    public void init(){
        timeValidator = new TimeValidator();
    }

    @Test
    public void testCorrectTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-12-31";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testEqualTimeExpectTrue() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-11-11";
        final String message = "end date should be greater than end date";
        Assert.assertTrue(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testFromTimeGreaterThanToTimeExpectFalse() throws ParseException {
        final String startDate = "2016-11-11";
        final String endDate = "2016-10-31";
        final String message = "end date should be greater than end date";
        Assert.assertFalse(message, timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testCorrectDateFormatExpectTrue()
    {
        final String correctDate = "2016-10-10";
        final boolean expected = true;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }

    @Test
    public void testInCorrectDateFormatExpectFalse()
    {
        final String correctDate = "201611-10-102";
        final boolean expected = false;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        final String message = "the format of date have to be YYYY-MM-DD";
        Assert.assertEquals(message, expected, actual);
    }
}
