package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.impl.UserServiceImpl;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDao userDao;

    @Mock
    User user;

    private List<User> users;

    private List<Room> rooms;

    @Before
    public void beforeTests() {
        // todo: use BlockJUnit4ClassRunnerWithParameters
        MockitoAnnotations.initMocks(this);
        users = UserUtils.getListOfUser();
        rooms = RoomUtils.getListOfRooms();
    }

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

    // todo: write test for ListWithNoIds and expect some error;

    @Test
    public void testFindAll() {
        Mockito.when(userDao.findAll()).thenReturn(users);
        userService.findAll();

        verify(userDao, times(1)).findAll();
        Assert.assertArrayEquals(UserMessages.FIND_ALL_ERROR, userService.findAll().toArray(), users.toArray());
    }


    // todo: change it to use mocks and not delete existing entries and also read about @Transactional annotation;
//    @Test(expected = IllegalAccessException.class)
//    @Transactional
//    public void testDeleteUserById() {
//        System.out.println("Testing deleteUserById() method in userService. Test correct number of calls for deleteUserById()");
//
//        Mockito.doThrow(new IllegalAccessException()).when(this.userDao).deleteUserById(1L);
//        userService.deleteUserById(1L);
//        Mockito.verify(userDao, times(1)).deleteUserById(1L);
//    }

    @Test
    // todo: use JParams especially @Parameters
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

        Date startDate = new Date();
        Date endDate = new Date();
        Room room = new Room();

        when(userDao.findActiveUsers(startDate, endDate, room)).thenReturn(users);

        List<User> userList = userService.getActiveUsers(startDate, endDate, room);

        verify(userDao, times(1)).findActiveUsers(startDate, endDate, room);

        Assert.assertArrayEquals("Users should be equal", userList.toArray(), users.toArray());
    }

    @Test
    public void testGetActiveRooms() {

        when(user.getRooms()).thenReturn(rooms);
        userService.getActiveRooms(user);

        verify(user, times(1)).getRooms();

    }

    @Test
    public void testEnableChildren() {

        // todo: implement;
    }

}
