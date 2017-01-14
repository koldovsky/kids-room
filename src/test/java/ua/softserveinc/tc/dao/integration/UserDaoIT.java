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
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.messaging.UserMessages;
import ua.softserveinc.tc.util.UserUtils;

import java.util.Arrays;
import java.util.List;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class UserDaoIT {

    @Autowired
    private UserDao userDao;

    @DatabaseSetup(value = "classpath:userDao/no-user.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/no-user.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllUsersByRoleIfThereIsNoUser() {
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_ROLE_IF_THERE_IS_NO_USER,
                0, userDao.findAllUsersByRole(Role.USER).size());
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_ROLE_IF_THERE_IS_NO_USER,
                0, userDao.findAllUsersByRole(Role.ADMINISTRATOR).size());
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_ROLE_IF_THERE_IS_NO_USER,
                0, userDao.findAllUsersByRole(Role.MANAGER).size());
    }

    @DatabaseSetup(value = "classpath:userDao/only-user-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/only-user-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllUsersByUserRole() {
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_USER_ROLE,
                3, userDao.findAllUsersByRole(Role.USER).size());
    }

    @DatabaseSetup(value = "classpath:userDao/only-manager-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/only-manager-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllUsersByManagerRole() {
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_MANAGER_ROLE,
                3, userDao.findAllUsersByRole(Role.MANAGER).size());
    }

    @DatabaseSetup(value = "classpath:userDao/only-administrators-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/only-administrators-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllUsersByAdministratorRole() {
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_ADMINISTRATOR_ROLE,
                3, userDao.findAllUsersByRole(Role.ADMINISTRATOR).size());
    }

    @DatabaseSetup(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllUsersByRole() {
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_USER_ROLE,
                1, userDao.findAllUsersByRole(Role.USER).size());
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_MANAGER_ROLE,
                2, userDao.findAllUsersByRole(Role.MANAGER).size());
        Assert.assertEquals(UserMessages.FIND_All_USERS_BY_ADMINISTRATOR_ROLE,
                2, userDao.findAllUsersByRole(Role.ADMINISTRATOR).size());
    }

    @DatabaseSetup(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAll() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<User> users = userDao.findAll(ids);
        System.out.println(users);
        List<User> expectedUsers = Arrays.asList(UserUtils.getListOfUser().get(0),
                UserUtils.getListOfUser().get(1), UserUtils.getListOfUser().get(2));
        Assert.assertEquals(expectedUsers, users);
    }

    @DatabaseSetup(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userDao/different-users-with-different-roles.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetUserByName() {
        User user = userDao.getUserByName("Alan", "Bom");
        User expectedUser = UserUtils.getListOfUser().get(3);
        Assert.assertEquals(UserMessages.GET_USER_BY_NAME, expectedUser, user);
    }

}
