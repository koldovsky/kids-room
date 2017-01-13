package ua.softserveinc.tc.dao.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.impl.ChildDaoImpl;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.messaging.ChildMessages;

import java.text.ParseException;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class ChildDaoTest {

    @Mock
    private ChildDaoImpl childDao;

    private List<Child> child;

    @Mock
    private Room room;

    @Before
    public void initialization() throws ParseException {
        MockitoAnnotations.initMocks(this);
        child = ChildsUtils.getListOfChilgren();
    }

    @Test
    public void testCorrectNumberOfInvokingGetActiveChildrenInRoom() {
        when(childDao.getActiveChildrenInRoom(room)).thenReturn(child);

        childDao.getActiveChildrenInRoom(room);

        verify(childDao, times(1)).getActiveChildrenInRoom(room);
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM,
                child, childDao.getActiveChildrenInRoom(room));
    }

    @Test
    public void testCorrectResultWhenInvokeGetActiveChildrenInRoom() {
        when(childDao.getActiveChildrenInRoom(room)).thenReturn(child);

        Assert.assertEquals(ChildMessages.INCORRECT_LIST_OF_CHILDREN, childDao.getActiveChildrenInRoom(room), child);
    }


}
