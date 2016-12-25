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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Vitalij Fedyna on 25.12.16.
 */
public class RoomValidatorTest {

  private RoomValidator roomValidator;

  @Mock
  private BookingDto bookingDto;

  @Mock
  private RoomDto roomDto;

  @Mock
  private Errors errors;

  @Before
  public void setRoomValidator() {
    MockitoAnnotations.initMocks(this);
    roomValidator = new RoomValidatorImpl();
    roomDto = new RoomDto();
    errors = new BindException(roomDto, "roomDto");
    roomDto.setName("Room");
    roomDto.setAddress("Pasternaka 4");
    roomDto.setCapacity(4);
    roomDto.setCity("Lviv");
    roomDto.setPhoneNumber("+380672211204");
    roomDto.setWorkingHoursStart("07:00");
    roomDto.setWorkingHoursEnd("20:00");

  }

  @Test
  public void supports() {
    assertTrue(roomValidator.supports(RoomDto.class));
    assertFalse(roomValidator.supports(Object.class));
    assertFalse(roomValidator.supports(ChildDto.class));
  }

  @Test
  public void testValidDto() {
    roomValidator.validate(roomDto, errors);
    assertFalse(errors.hasErrors());
  }

  @Test
  public void testCapacity() {
    roomDto.setCapacity(0);
    roomValidator.validate(roomDto, errors);
    assertTrue(errors.hasErrors());
    roomDto.setCapacity(201);
    roomValidator.validate(roomDto, errors);
    assertTrue(errors.hasErrors());
    Assert.assertEquals(ValidationConstants.ROOM_MIN_MAX_CAPACITY,
        errors.getFieldError(ValidationConstants.ROOM_CAPACITY).getCode());
  }

  @Test
  public void testStartDateBiggerThanEnd() {
    roomDto.setWorkingHoursStart("21:00");
    roomDto.setWorkingHoursEnd("08:00");
    roomValidator.validate(roomDto, errors);
    Assert.assertTrue(errors.hasErrors());
    Assert.assertEquals(ValidationConstants.TIME_IS_NOT_VALID,
        errors.getFieldError(ValidationConstants.TIME_FIELD).getCode());
  }

  @Test
  public void testWrongDate() {
    roomDto.setWorkingHoursEnd("String");
    roomDto.setWorkingHoursStart("10:99");
    roomValidator.validate(roomDto, errors);
    Assert.assertTrue(errors.hasErrors());
    Assert.assertEquals(ValidationConstants.ROOM_WRONG_TIME_FORMAT,
        errors.getFieldError(ValidationConstants.TIME_FIELD).getCode());
  }

  @Test
  public void testRegex() {
    roomDto.setName("!@#!#!@#");
    roomDto.setAddress("Wrong @!~");
    roomDto.setCity("2131231");
    roomDto.setPhoneNumber("+312asf");
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
    roomDto.setName("Q");
    roomDto.setAddress("MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
        "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
        "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols" +
        "MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols MoreThan255symbols");
    roomDto.setCity("L");
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
    bookingDto = new BookingDto();
    roomValidator.validate(bookingDto, errors);
    Assert.assertTrue(errors.hasErrors());
    Assert.assertEquals(ValidationConstants.ROOM_WRONG_CAST_MSG,
        errors.getFieldError(ValidationConstants.ROOM_NAME).getCode());
  }
}
