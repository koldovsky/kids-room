package ua.softserveinc.tc.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.*;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.impl.UserServiceImpl;
import ua.softserveinc.tc.util.UserUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@Category(UnitTest.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDao userDao;

    @Mock
    User user;

    @Mock
    private List<User> users;

    @Mock
    private List<Room> rooms;

    @Before
    public void beforeTests() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllByListOfIds() {
        List<Long> listOfIds = Arrays.asList(1L, 2L, 3L);
        Mockito.when(userDao.findAll(listOfIds)).thenReturn(users);
        userService.findAll(listOfIds);
        verify(userDao, times(1)).findAll(listOfIds);
    }

    @Test
    public void testFindAll() {
        Mockito.when(userDao.findAll()).thenReturn(users);
        userService.findAll();

        verify(userDao, times(1)).findAll();
    }

    @Test
    public void testGetUserByEmail() {
        String email = "somevalidemail@gmail.com";

        Mockito.when(user.getEmail()).thenReturn(email);
        Mockito.when(user.getFirstName()).thenReturn("Adam");
        Mockito.when(userDao.getUserByEmail(user.getEmail())).thenReturn(user);

        userService.getUserByEmail(email);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    public void testFindAllUsersByRole() {
        Role role = Role.USER;
        when(userDao.findAllUsersByRole(role)).thenReturn(users);

        userService.findAllUsersByRole(role);
        verify(userDao, times(1)).findAllUsersByRole(role);
    }

}
