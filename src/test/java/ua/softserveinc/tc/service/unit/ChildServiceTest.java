package ua.softserveinc.tc.service.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.impl.ChildServiceImpl;

import java.text.ParseException;
import java.util.List;

@Category(UnitTest.class)
public class ChildServiceTest {

    @InjectMocks
    private ChildServiceImpl childService;

    @Mock
    private ChildDao childDao;

    @Mock
    private List<Child> childs;

    @Mock
    private Room room;

    @Before
    public void beforeTest() throws ParseException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetActiveChildrenInRoom() {
        Mockito.when(childDao.getActiveChildrenInRoom(room)).thenReturn(childs);

        childDao.getActiveChildrenInRoom(room);

        Mockito.verify(childDao).getActiveChildrenInRoom(room);
    }

}
