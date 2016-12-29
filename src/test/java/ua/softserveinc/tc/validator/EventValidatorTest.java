package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * Created by IhorTovpinets on 25.12.2016.
 */
public class EventValidatorTest {

    @Mock
    private EventDto eventDto;

    @Mock
    private MonthlyEventDto monthlyEventDto;

    @Mock
    private RecurrentEventDto recurrentEventDto;

    @Mock
    private Errors errorsForSingle;

    @Mock
    private Errors errorsForMonthly;

    @Mock
    private Errors errorsForWeekly;

    @Mock
    private RoomService roomService;

    @Mock
    private Room room;

    @InjectMocks
    private EventValidator eventValidator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventDto = new EventDto();
        initEventDto();
        errorsForSingle = new BindException(eventDto, "event");
        errorsForMonthly = new BindException(monthlyEventDto, "eventMonthly");
        errorsForWeekly = new BindException(recurrentEventDto, "eventRecurrent");
        monthlyEventDto.setRecurrentType(EventConstants.TypeOfRecurentEvent.MONTHLY);
        recurrentEventDto.setRecurrentType(EventConstants.TypeOfRecurentEvent.WEEKLY);
    }

    public void initEventDto() {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, 2);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 4);
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        eventDto.setName("NameForEvent");
        eventDto.setEndTime(sdf.format(end.getTime()));
        eventDto.setStartTime(sdf.format(start.getTime()));
        eventDto.setColor("Green");
        eventDto.setDescription("");
        eventDto.setRoomId(1L);
        when(roomService.findById(1L)).thenReturn(room);
        when(room.isActive()).thenReturn(true);
        monthlyEventDto = new MonthlyEventDto(eventDto);
        recurrentEventDto = new RecurrentEventDto(eventDto);
        end.add(Calendar.DAY_OF_MONTH, 2);
        recurrentEventDto.setEndTime(sdf.format(end.getTime()));
        String recurrentDays = "Mon";
        recurrentEventDto.setDaysOfWeek(recurrentDays);
    }

    @Test
    public void testEventRoom() {
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has No Error", !errorsForSingle.hasFieldErrors(ValidationConstants.ROOM_ID));
        when(room.isActive()).thenReturn(false);
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("HasErrors", errorsForSingle.hasErrors());
        Assert.assertEquals("Room is not Active", ValidationConstants.EVENT_INACTIVE_ROOM_ERROR_MSG,
                errorsForSingle.getFieldError(ValidationConstants.ROOM_ID).getCode());
    }

    @Test
    public void testTitle() {
        eventDto.setName("");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has name error", errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_TITLE));
        Assert.assertEquals("name is empty", ValidationConstants.EVENT_EMPTY_TITLE_MSG,
                errorsForSingle.getFieldError(ValidationConstants.EVENT_TITLE).getCode());
    }

    @Test
    public void testColor() {
        eventDto.setColor("");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has color error", errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_COLOR));
        Assert.assertEquals("color is empty", ValidationConstants.EMPTY_FIELD_MSG,
                errorsForSingle.getFieldError(ValidationConstants.EVENT_COLOR).getCode());
    }

    @Test
    public void isValid() {
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Dto is valid", !errorsForSingle.hasErrors());
    }

    @Test
    public void testInvalidDescription() {
        eventDto.setDescription("The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ " +
                "The length should be more then 250 chars :/ ");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has description Error", errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_DESCRIPTION));
        Assert.assertEquals("description is more then 250 chars",
                ValidationConstants.EVENT_DESCRIPTION_LENGTH_ERROR_MSG,
                errorsForSingle.getFieldError(ValidationConstants.EVENT_DESCRIPTION).getCode());
    }

    @Test
    public void testOkDescription() {
        eventDto.setDescription("This description is ok");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Description has no Error", !errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_DESCRIPTION));
    }

    @Test
    public void testStartTimeEmpty() {
        eventDto.setStartTime("");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has startTime Error", errorsForSingle.hasFieldErrors(ValidationConstants.START_TIME));
        Assert.assertEquals("startTime is empty", ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG,
                errorsForSingle.getFieldError(ValidationConstants.START_TIME).getCode());
    }

    @Test
    public void testEndTimeEmpty() {
        eventDto.setEndTime("");
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has endTime Error", errorsForSingle.hasFieldErrors(ValidationConstants.END_TIME));
        Assert.assertEquals("endTime is empty", ValidationConstants.EVENT_DATE_FORMAT_INVALID_MSG,
                errorsForSingle.getFieldError(ValidationConstants.END_TIME).getCode());
    }

    @Test
    public void testTimeBeforePresent() {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -10);
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        eventDto.setStartTime(sdf.format(start.getTime()));
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("Has startTime Error", errorsForSingle.hasFieldErrors(ValidationConstants.START_TIME));
        Assert.assertEquals("startTime is empty", ValidationConstants.EVENT_PAST_TIME_CREATION_MSG,
                errorsForSingle.getFieldError(ValidationConstants.START_TIME).getCode());
        Assert.assertTrue("Has one error", errorsForSingle.getAllErrors().size() < 2);
    }

    @Test
    public void testEndTimeBeforeStart() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.add(Calendar.MINUTE, 2);
        end.add(Calendar.MINUTE, 1);
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        eventDto.setEndTime(sdf.format(end.getTime()));
        eventDto.setStartTime(sdf.format(start.getTime()));
        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("error with start<end time", errorsForSingle.hasFieldErrors(ValidationConstants.START_TIME));
        Assert.assertEquals("startTime is empty", ValidationConstants.EVENT_END_MUST_BIGGER_ONE_MINUTE_MSG,
                errorsForSingle.getFieldError(ValidationConstants.START_TIME).getCode());
        Assert.assertTrue("Has one error", errorsForSingle.getAllErrors().size() < 2);
    }

    @Test
    public void testClassCastException() {
        eventValidator.validate(monthlyEventDto, errorsForMonthly);
        Assert.assertTrue("classCastException", !errorsForMonthly.hasFieldErrors(ValidationConstants.EVENT_TITLE));

        eventValidator.validate(eventDto, errorsForSingle);
        Assert.assertTrue("classCastException", !errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_TITLE));

        eventValidator.validate(recurrentEventDto, errorsForWeekly);
        Assert.assertTrue("classCastException", !errorsForWeekly.hasFieldErrors(ValidationConstants.EVENT_TITLE));

        eventValidator.validate(roomService, errorsForSingle);
        Assert.assertTrue("classCastException", errorsForSingle.hasFieldErrors(ValidationConstants.EVENT_TITLE));
        Assert.assertEquals("class is not ok", ValidationConstants.EVENT_CAST_EXCEPTION,
                errorsForSingle.getFieldError(ValidationConstants.EVENT_TITLE).getCode());
    }

    @Test
    public void testMonthlyEventNullPointerForDays() {
        eventValidator.validate(monthlyEventDto, errorsForMonthly);
        Assert.assertTrue("Has days for recurrent", errorsForMonthly.hasFieldErrors(ValidationConstants.MONTH_RECURRENT_DAYS));
        Assert.assertEquals("null pointer", ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT,
                errorsForMonthly.getFieldError(ValidationConstants.MONTH_RECURRENT_DAYS).getCode());
    }

    @Test
    public void testMonthlyEventDays() {
        Set<Integer> recurrentDays = new HashSet<>();
        recurrentDays.add(1);
        monthlyEventDto.setDaysOfTheMonth(recurrentDays);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH, 2);
        end.add(Calendar.MINUTE, 10);
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        monthlyEventDto.setEndTime(sdf.format(end.getTime()));
        eventValidator.validate(monthlyEventDto, errorsForMonthly);
        Assert.assertTrue("Its ok",!errorsForMonthly.hasErrors());
    }

    @Test
    public void testEndOneDayLater() {
        Set<Integer> recurrentDays = new HashSet<>();
        recurrentDays.add(1);
        monthlyEventDto.setDaysOfTheMonth(recurrentDays);
        Calendar end = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_FORMAT);
        monthlyEventDto.setEndTime(sdf.format(end.getTime()));
        eventValidator.validate(monthlyEventDto, errorsForMonthly);
        Assert.assertTrue("at least one day later",errorsForMonthly.hasErrors());
        Assert.assertEquals(errorsForMonthly.getFieldError(ValidationConstants.MONTH_RECURRENT_DAYS).getCode(),
                ValidationConstants.EVENT_RECCURRENT_END_MUST_BIGER_ONE_DAY_MSG);
    }

    @Test
    public void testMonthlyEventDaysEmpty() {
        Set<Integer> recurrentDays = new HashSet<>();
        monthlyEventDto.setDaysOfTheMonth(recurrentDays);
        eventValidator.validate(monthlyEventDto, errorsForMonthly);
        Assert.assertTrue("Has days for recurrent",
                errorsForMonthly.hasFieldErrors(ValidationConstants.MONTH_RECURRENT_DAYS));
        Assert.assertEquals("empty list", ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT,
                errorsForMonthly.getFieldError(ValidationConstants.MONTH_RECURRENT_DAYS).getCode());
    }

    @Test
    public void testWeeklyEventDays() {
        eventValidator.validate(recurrentEventDto, errorsForWeekly);
        Assert.assertTrue(!errorsForWeekly.hasErrors());
        recurrentEventDto.setDaysOfWeek("");
        eventValidator.validate(recurrentEventDto, errorsForWeekly);
        Assert.assertTrue(errorsForWeekly.hasErrors());
        Assert.assertEquals("empty list", ValidationConstants.NO_DAYS_FOR_RECURRENT_EVENT,
                errorsForWeekly.getFieldError(ValidationConstants.WEEK_RECURRENT_DAYS).getCode());
    }
}
