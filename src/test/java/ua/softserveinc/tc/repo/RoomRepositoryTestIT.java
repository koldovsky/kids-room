package ua.softserveinc.tc.repo;

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
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class RoomRepositoryTestIT {

    @Autowired
    private RoomDao roomRepository; // to test when changing one method in roomRepository to method in roomDao

    @DatabaseSetup(value = "classpath:roomRepository/no-rooms.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:roomRepository/no-rooms.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByIsActiveTrueIfThereAreNoRooms() {
        List<Room> listRooms = roomRepository.findByIsActiveTrue();
        Assert.assertTrue(listRooms.isEmpty());
    }

    @DatabaseSetup(value = "classpath:roomRepository/multiple-rooms.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:roomRepository/multiple-rooms.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByIsActiveTrueIfThereAreMultipleRooms() {
        List<Room> roomList = roomRepository.findByIsActiveTrue();
        Assert.assertEquals(2, roomList.size());
    }

}
