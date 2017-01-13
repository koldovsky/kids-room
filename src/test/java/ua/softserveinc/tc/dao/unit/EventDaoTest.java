package ua.softserveinc.tc.dao.unit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.impl.EventDaoImpl;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.util.EventUtils;

import java.text.ParseException;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class EventDaoTest {

    @Mock
    private EventDaoImpl eventDao;

    @Before
    public void initialization() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMaxRecurrentId() {
        when(eventDao.getMaxRecurrentId()).thenReturn(1L);

        eventDao.getMaxRecurrentId();

        verify(eventDao, times(1)).getMaxRecurrentId();
    }

    @Test
    @Parameters(method = "getEventList")
    public void testGetRecurrentEventByRecurrentId(List<Event> eventList, Long id) throws ParseException {
        when(eventDao.getRecurrentEventByRecurrentId(id)).thenReturn(eventList);

        eventDao.getRecurrentEventByRecurrentId(id);

        verify(eventDao, times(1)).getRecurrentEventByRecurrentId(id);
        Assert.assertEquals(eventList, eventDao.getRecurrentEventByRecurrentId(id));
    }

    private static final Object[] getEventList() throws ParseException {
        return $(
                $(EventUtils.getListOfEvents(), 1L)
        );
    }

}
