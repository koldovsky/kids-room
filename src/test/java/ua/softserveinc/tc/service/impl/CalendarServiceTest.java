package ua.softserveinc.tc.service.impl;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.*;

@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class CalendarServiceTest {

    @InjectMocks
    private CalendarServiceImpl calendarService;

    @Mock
    private Event event;

    @Mock
    private EventDao eventDao;

    @Mock
    private RoomService roomService;

    @Mock
    private RoomDao roomDao;

    @Mock
    private static List<Event> eventList;

    @Before
    public void initialization() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @Parameters(method = "getEvents")
    public void testFindEventByRoomId(List<Event> listOfEvent, Long roomId) {
        when(roomService.findEntityById(roomId)).thenReturn(new Room());
        when(roomService.findEntityById(roomId).getEvents()).thenReturn(listOfEvent);

        verify(roomService, times(0)).findEntityById(roomId);

        roomService.findEntityById(roomId);
        verify(roomService, times(1)).findEntityById(roomId);
    }

    private static final Object[] getEvents() {
        return $(
                $(eventList, 0L)
        );
    }

}
