package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.impl.ChildServiceImpl;
import ua.softserveinc.tc.util.ChildsUtils;

import java.util.List;

public class ChildServiceTest {

    @InjectMocks
    private ChildServiceImpl childService;

    @Mock
    private ChildDao childDao;

    private List<Child> childs;

    @Before
    public void beforeTest()
    {
        MockitoAnnotations.initMocks(this);
        childs = ChildsUtils.getListOfChilgren();
    }

    @Test
    public void testGetActiveChildrenInRoom()
    {
        Room room = new Room();

        Mockito.when(childDao.getActiveChildrenInRoom(room)).thenReturn(childs);

        List<Child> children = childService.getActiveChildrenInRoom(room);

        Mockito.verify(childDao).getActiveChildrenInRoom(room);

        Assert.assertArrayEquals(children.toArray(), childs.toArray());
    }

}
