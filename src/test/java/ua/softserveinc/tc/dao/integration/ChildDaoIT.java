package ua.softserveinc.tc.dao.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.categories.IntegrationTest;
import ua.softserveinc.tc.config.TestBaseConfigClass;
import ua.softserveinc.tc.dao.ChildDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.messaging.ChildMessages;

import static ua.softserveinc.tc.dao.paths.ChildDaoITPath.*;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class ChildDaoIT {

    @Autowired
    private ChildDao childDao;

    @Autowired
    private RoomDao roomDao;

    @DatabaseSetup(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomIsNotActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_NOT_ACTIVE,
                0, childDao.getActiveChildrenInRoom(roomDao.findById(1L)).size());
    }

    @DatabaseSetup(value = ONE_ACTIVE_BOOKING, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = ONE_ACTIVE_BOOKING, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomIsActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                1, childDao.getActiveChildrenInRoom(roomDao.findById(1L)).size());
    }

    @DatabaseSetup(value = MULTIPLE_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = MULTIPLE_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomAreActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                2, childDao.getActiveChildrenInRoom(roomDao.findById(1L)).size());
    }

    @DatabaseSetup(value = MULTIPLE_NO_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = MULTIPLE_NO_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomAreNoActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                0, childDao.getActiveChildrenInRoom(roomDao.findById(1L)).size());
    }


}
