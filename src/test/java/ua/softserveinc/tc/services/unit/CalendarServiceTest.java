package ua.softserveinc.tc.services.unit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.repo.EventRepository;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.impl.CalendarServiceImpl;
import ua.softserveinc.tc.service.impl.RoomServiceImpl;
import ua.softserveinc.tc.util.EventUtils;
import ua.softserveinc.tc.util.RoomUtils;

import java.util.Arrays;
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
    private EventRepository eventRepository;

    @Mock
    private RoomServiceImpl roomService;

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
