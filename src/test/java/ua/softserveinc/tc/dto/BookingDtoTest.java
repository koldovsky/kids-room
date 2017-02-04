package ua.softserveinc.tc.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.RateService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.BookingsCharacteristics;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.TwoTuple;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.RecurrentBookingValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class, BookingDto.class})
public class BookingDtoTest {

    @Test
    public void testSetAndGetId () {

    }

    @Test
    public void testSetAndGetDate () {

    }

    @Test
    public void testSetAndGetEndDate () {

    }

    @Test
    public void testSetAndGetStartTime() {

    }

    @Test
    public void testSetAndGetEndTime() {

    }

    @Test
    public void testSetAndGetStartTimeMillis() {

    }

    @Test
    public void testSetAndGetEndTimeMillis() {

    }

    @Test
    public void testSetAndGetDurationBooking() {

    }

    @Test
    public void testSetAndGetKidName() {

    }

    @Test
    public void testSetAndGetRoomName() {

    }

    @Test
    public void testSetAndGetDuration() {

    }

    @Test
    public void testSetAndGetIdChild() {

    }

    @Test
    public void testSetAndGetSum() {

    }

    @Test
    public void testSetAndGetDurationLong() {

    }

    @Test
    public void testSetAndGetBookingState() {

    }

    @Test
    public void testSetAndGetComment() {

    }

    @Test
    public void testSetAndGetRecurrentId() {

    }

    @Test
    public void testSetAndGetUserId() {

    }

    @Test
    public void testSetAndGetKidId() {

    }

    @Test
    public void testSetAndGetRoomId() {

    }

    @Test
    public void testSetAndGetDaysOfWeek() {

    }

    @Test
    public void testSetAndGetWeekDays() {

    }

    @Test
    public void testSetAndGetChild() {

    }

    @Test
    public void testSetAndGetUser() {

    }

    @Test
    public void testSetAndGetRoom() {

    }

    @Test
    public void testSetAndGetDateStartTime() {

    }

    @Test
    public void testSetAndGetDateEndTime() {
        
    }

}
