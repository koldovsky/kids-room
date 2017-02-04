package ua.softserveinc.tc.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.ChildDto;
import ua.softserveinc.tc.dto.RoomDto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class RoomValidatorTest {

    private RoomValidatorImpl roomValidator;

    @Mock
    private BookingDto bookingDto;

    @Mock
    private RoomDto roomDto;

    private Errors errors;

    @Before
    public void setRoomValidator() {
        MockitoAnnotations.initMocks(this);
        roomValidator = new RoomValidatorImpl();
        when(roomDto.getName()).thenReturn("Room");
        when(roomDto.getAddress()).thenReturn("Pasternaka");
        when(roomDto.getCapacity()).thenReturn(4);
        when(roomDto.getCity()).thenReturn("Lviv");
        when(roomDto.getPhoneNumber()).thenReturn("+380672211204");
        when(roomDto.getWorkingHoursStart()).thenReturn("07:00");
        when(roomDto.getWorkingHoursEnd()).thenReturn("20:00");
        when(roomDto.getManagers()).thenReturn("[{\"idIns\":\"manager2\",\"id\":\"3\",\"$$hashKey\":\"object:6\"}]");
        when(roomDto.getRate()).thenReturn("[{\"idIns\":\"rate1\",\"$$hashKey\":\"object:6\",\"hourRate\":2,\"priceRate\":30}]");
        errors = new BindException(roomDto, "roomDto");
    }

    @Test
    public void supports() {
        assertTrue(roomValidator.supports(RoomDto.class));
        assertFalse(roomValidator.supports(Object.class));
        assertFalse(roomValidator.supports(ChildDto.class));
    }

    @Test
    public void testWrongJSONFormatManagers() {
        when(roomDto.getManagers()).thenReturn("[{\"idIns\":\"manager1\",\"id\":\"Q\",\"$$hashKey\":\"object:3\"},{\"idIns\":\"manager2\",\"id\":\"1\",\"$$hashKey\":\"object:6\"}]");
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_MANAGER_INVALID,
                errors.getFieldError(ValidationConstants.MANAGERS_FIELD).getCode());
    }

    @Test
    public void testNullManagerId() {
        when(roomDto.getManagers()).thenReturn("[{\"idIns\":\"manager1\",\"$$hashKey\":\"object:3\"}]");
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_MANAGER_EMPTY,
                errors.getFieldError(ValidationConstants.MANAGERS_FIELD).getCode());
    }

    @Test
    public void testDuplicateManagers() {
        when(roomDto.getManagers()).thenReturn("[{\"idIns\":\"manager1\",\"id\":\"1\",\"$$hashKey\":\"object:3\"},{\"idIns\":\"manager2\",\"id\":\"1\",\"$$hashKey\":\"object:6\"}]");
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_MANAGER_DUPLICATE,
                errors.getFieldError(ValidationConstants.MANAGERS_FIELD).getCode());
    }

    @Test
    public void testWrongRate() {
        when(roomDto.getRate()).thenReturn("[{\"idIns\":\"rate1\",\"$$hashKey\":\"object:6\",\"hourRate\":0,\"priceRate\":null}]");
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_RATE_ERROR,
                errors.getFieldError(ValidationConstants.ROOM_RATE_FIELD).getCode());
    }


    @Test
    public void testValidDto() {
        roomValidator.validate(roomDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testCapacity() {
        when(roomDto.getCapacity()).thenReturn(0);
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        when(roomDto.getCapacity()).thenReturn(201);
        roomValidator.validate(roomDto, errors);
        assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_MIN_MAX_CAPACITY,
                errors.getFieldError(ValidationConstants.ROOM_CAPACITY).getCode());
    }

    @Test
    public void testStartDateBiggerThanEnd() {
        when(roomDto.getWorkingHoursStart()).thenReturn("21:00");
        when(roomDto.getWorkingHoursEnd()).thenReturn("08:00");
        roomValidator.validate(roomDto, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.TIME_IS_NOT_VALID,
                errors.getFieldError(ValidationConstants.TIME_FIELD).getCode());
    }

    @Test
    public void testWrongDate() {
        when(roomDto.getWorkingHoursStart()).thenReturn("String");
        when(roomDto.getWorkingHoursEnd()).thenReturn("10:99");
        when(errors.getFieldValue(ValidationConstants.TIME_FIELD)).thenReturn(
                ValidationConstants.TIME_FIELD);
        roomValidator.validate(roomDto, errors);

        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_WRONG_TIME_FORMAT,
                errors.getFieldError(ValidationConstants.TIME_FIELD).getCode());
    }

    @Test
    public void testRegex() {
        when(roomDto.getName()).thenReturn("!@#!#!@#");
        when(roomDto.getAddress()).thenReturn("Wrong @!~");
        when(roomDto.getCity()).thenReturn("2131231");
        when(roomDto.getPhoneNumber()).thenReturn("+312asf");
        roomValidator.validate(roomDto, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_INVALID_NAME_MSG,
                errors.getFieldError(ValidationConstants.ROOM_NAME).getCode());
        Assert.assertEquals(ValidationConstants.ROOM_INVALID_CITY_MSG,
                errors.getFieldError(ValidationConstants.ROOM_CITY).getCode());
        Assert.assertEquals(ValidationConstants.ROOM_INVALID_ADDRESS_MSG,
                errors.getFieldError(ValidationConstants.ROOM_ADDRESS).getCode());
        Assert.assertEquals(ValidationConstants.ROOM_INVALID_PHONE_MSG,
                errors.getFieldError(ValidationConstants.ROOM_PHONE_NUMBER).getCode());
    }

    @Test
    public void testFieldLenght() {
        when(roomDto.getName()).thenReturn("N");
        when(roomDto.getAddress()).thenReturn("MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
                "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
                "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
                "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols");
        when(roomDto.getCity()).thenReturn("L");
        roomValidator.validate(roomDto, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG,
                errors.getFieldError(ValidationConstants.ROOM_NAME).getCode());
        Assert.assertEquals(ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG,
                errors.getFieldError(ValidationConstants.ROOM_ADDRESS).getCode());
        Assert.assertEquals(ValidationConstants.ROOM_MIN_MAX_CHARACTERS_MSG,
                errors.getFieldError(ValidationConstants.ROOM_CITY).getCode());
    }

    @Test
    public void testWrongDto() {
        roomValidator.validate(bookingDto, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(ValidationConstants.ROOM_WRONG_CAST_MSG,
                errors.getFieldError(ValidationConstants.ROOM_NAME).getCode());
    }
}
