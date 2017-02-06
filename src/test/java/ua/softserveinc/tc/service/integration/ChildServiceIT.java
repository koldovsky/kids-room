package ua.softserveinc.tc.service.integration;

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
import ua.softserveinc.tc.messaging.ChildMessages;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;

import static ua.softserveinc.tc.paths.ChildDaoITPath.*;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class ChildServiceIT {

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @DatabaseSetup(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomIsNotActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_NOT_ACTIVE,
                0, childService.getActiveChildrenInRoom(roomService.findEntityById(1L)).size());
    }

    @DatabaseSetup(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = NO_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test(expected = ResourceNotFoundException.class)
    public void testGetActiveChildrenInRoomWhenThereIsNoSuchRoom() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_NOT_ACTIVE,
                0, childService.getActiveChildrenInRoom(roomService.findEntityById(10L)).size());
    }

    @DatabaseSetup(value = ONE_ACTIVE_BOOKING, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = ONE_ACTIVE_BOOKING, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomIsActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                1, childService.getActiveChildrenInRoom(roomService.findEntityById(1L)).size());
    }

    @DatabaseSetup(value = MULTIPLE_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = MULTIPLE_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomAreActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                2, childService.getActiveChildrenInRoom(roomService.findEntityById(1L)).size());
    }

    @DatabaseSetup(value = MULTIPLE_NO_ACTIVE_BOOKINGS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = MULTIPLE_NO_ACTIVE_BOOKINGS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetActiveChildrenInRoomWhenRoomAreNoActive() {
        Assert.assertEquals(ChildMessages.INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE,
                0, childService.getActiveChildrenInRoom(roomService.findEntityById(1L)).size());
    }
}
