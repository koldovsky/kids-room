package ua.softserveinc.tc.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.Collections;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RecurrentBookingValidatorTest {

    @Mock
    private RoomService roomService;

    @Mock
    private BookingDto bookingDto;

    @Mock Room room;

    @InjectMocks
    private InputDateTimeValidatorImpl inputDateValidator;

    @Before
    public void initValidBookingDto() {
        when(bookingDto.getStartTime()).thenReturn("2018-01-27T15:00:00");
        when(bookingDto.getEndTime()).thenReturn("2018-01-27T16:00:00");
    }
}
