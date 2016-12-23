package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;

import java.text.ParseException;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class DateValidatorTest {

    @Autowired
    public TimeValidator timeValidator;

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
