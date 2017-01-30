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
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class UserRepositoryTestIT {

    @Autowired
    private UserService userRepository;

    @DatabaseSetup(value = "classpath:userRepository/no-user.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userRepository/no-user.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void findByActiveTrueAndRoleNotWhenThereAreNoUsers() {

        List<User> users = userRepository.findByActiveTrueAndRoleNot(Role.ADMINISTRATOR);
        Assert.assertTrue(users.isEmpty());
    }

    @DatabaseSetup(value = "classpath:userRepository/only-manager-user.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userRepository/only-manager-user.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void findByActiveTrueAndRoleNotManager() {

        List<User> users = userRepository.findByActiveTrueAndRoleNot(Role.MANAGER);
        Assert.assertTrue(users.isEmpty());
    }

    @DatabaseSetup(value = "classpath:userRepository/multiple-user.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:userRepository/multiple-user.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void findByActiveTrueAndRoleNotAdministrator() {

        List<User> users = userRepository.findByActiveTrueAndRoleNot(Role.ADMINISTRATOR);
        Assert.assertEquals(3, users.size());
    }

}
