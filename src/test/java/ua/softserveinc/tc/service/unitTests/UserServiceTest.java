package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.TestBaseConfigClass;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.messaging.UserMessages;
import ua.softserveinc.tc.service.impl.UserServiceImpl;
import ua.softserveinc.tc.util.RoomUtils;
import ua.softserveinc.tc.util.UserUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by melancholiya.
 */

/**
 *  (+) List<User> findAll(List<Long> ids);
 * <p>
 *  (+/-) void deleteUserById(Long id);
 * <p>
 *  (+) User getUserByEmail(String email);
 * <p>
 * User getUserByName(String firstName, String lastName);
 * <p>
 * void createWithEncoder(User user);
 * <p>
 *  (+) List<User> findAllUsersByRole(Role role);
 * <p>
 *  (+) List<User> getActiveUsers(Date startDate, Date endDate, Room room);
 * <p>
 * List<Room> getActiveRooms(User user);
 * <p>
 * List<Child> getEnabledChildren(User user);
 */

// todo: write where each test is used;

@DirtiesContext
@ContextConfiguration(classes = TestBaseConfigClass.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDao userDao;

    @Mock
    User user;

    @Spy
    List<User> users = new ArrayList<>();

    @Spy
    List<Room> rooms = new ArrayList<>();

    @Before
    public void beforeTests() {
        // BlockJUnit4ClassRunnerWithParameters
        MockitoAnnotations.initMocks(this);
        users = UserUtils.getListOfUser();
        rooms = RoomUtils.getListOfRooms();
    }

    // List<User> findAll(List<Long> ids);
    // is using in update(manager)Controller;
    @Test
    public void testFindAllByListOfIds() {
        List<Long> listOfIds = UserUtils.getListOfIds3();

        // todo: ask about errors, where they are handle,
        // todo: for example, when length of list is more then there is an ids error should occur;
        Mockito.when(userDao.findAll(listOfIds)).thenReturn(users);
        userService.findAll(listOfIds);
        verify(userDao, times(1)).findAll(listOfIds);
        Assert.assertArrayEquals(UserMessages.FIND_ALL_BY_ID_ERROR, userService.findAll(listOfIds).toArray(), users.toArray());

    }

    @Test
    public void testFindAll() {
        Mockito.when(userDao.findAll()).thenReturn(users);
        userService.findAll();

        verify(userDao, times(1)).findAll();
        Assert.assertArrayEquals(UserMessages.FIND_ALL_ERROR, userService.findAll().toArray(), users.toArray());
    }


    // todo: change it to use mocks and not delete existing entries and also read about @Transactional annotation;
//    @Test
//    @Transactional
//    public void testDeleteUserById() {
//        System.out.println("Testing deleteUserById() method in userService. Test correct number of calls for deleteUserById()");
//
//        Mockito.when(this.userService).
//                Mockito.verify(userDao, times(1)).deleteUserById(1L);
//    }

    @Test
    // @Parameters
    public void testGetUserByEmail() {
        String email = "somevalidemail@gmail.com";

        System.out.println("Testing getUserByEmail(String email) method in userService. Test correct number of " +
                "calls for getUserByEmail(String email)");

        Mockito.when(user.getEmail()).thenReturn(email);
        Mockito.when(user.getFirstName()).thenReturn("Jolly");

        Mockito.when(userDao.getUserByEmail(user.getEmail())).thenReturn(user);

        User returnedUser = userService.getUserByEmail(user.getEmail());

        Assert.assertEquals("Method getUserByEmail() doesn't work correct", returnedUser, user);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    public void testFindAllUsersByRole() {
        System.out.println("Testing findAllUsersByRole(Role role) method in userService. " +
                "Test correct number of calls for findAllUsersByRole(Role role)");

        Role role = Role.USER;
        when(userDao.findAllUsersByRole(role)).thenReturn(users);

        userService.findAllUsersByRole(role);

        verify(userDao, times(1)).findAllUsersByRole(role);

        Assert.assertArrayEquals("Method findAllUsersByRole(Role role) doesn't work correctly",
                                userService.findAllUsersByRole(role).toArray(),
                                users.toArray());

    }

    @Test
    public void testGetActiveUsers() {

        Date startDate = new Date(); // change start date;
        Date endDate = new Date();  // change end date;
        Room room = new Room(); // test room;

        when(userDao.findActiveUsers(startDate, endDate, room)).thenReturn(users);

        List<User> userList = userService.getActiveUsers(startDate, endDate, room);

        verify(userDao, times(1)).findActiveUsers(startDate, endDate, room);

        Assert.assertArrayEquals("Users should be equal", userList.toArray(), users.toArray());
    }

    @Test
    public void testGetActiveRooms() {

        // to test this I need to use integration test;

        when(user.getRooms()).thenReturn(rooms);
        userService.getActiveRooms(user);

        verify(user, times(1)).getRooms();

    }

    @Test
    public void testEnableChilden() {

        System.out.println("");
        userService.getEnabledChildren(user);

    }

    /**
     *
     *

     void deleteUserById(Long id);

     User getUserByEmail(String email);

     void createWithEncoder(User user);

     List<User> findAllUsersByRole(Role role);

     List<User> getActiveUsers(Date startDate, Date endDate, Room room);

     List<Room> getActiveRooms(User user);

     List<Child> getEnabledChildren(User user);
     * */

}
