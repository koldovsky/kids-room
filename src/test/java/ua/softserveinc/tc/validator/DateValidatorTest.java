package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateValidatorTest {

    public TimeValidator timeValidator;

    @Before
    public void init(){
        timeValidator = new TimeValidator();
    }

    @Test
    public void testCorrectTimeExpectTrue()
    {
        final String startDate = "2016-11-11";
        final String endDate = "2016-12-31";
        Assert.assertTrue(timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testFromTimeGreaterThanToTimeExpectFalse()
    {
        final String startDate = "2016-11-11";
        final String endDate = "2016-10-31";
        Assert.assertFalse(timeValidator.validateDate(startDate, endDate));
    }

    @Test
    public void testCorrectDateFormatExpectTrue()
    {
        final String correctDate = "2016-10-10";
        final boolean expected = true;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInCorrectDateFormatExpectFalse()
    {
        final String correctDate = "201611-10-102";
        final boolean expected = false;
        final boolean actual = timeValidator.validateDateFormat(correctDate);
        Assert.assertEquals(expected, actual);
    }
}

