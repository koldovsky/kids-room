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
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.UserUtils;

import static ua.softserveinc.tc.paths.TokenServiceITPath.FIND_BY_EXISTING_USER;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class TokenServiceIT {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @DatabaseSetup(value = FIND_BY_EXISTING_USER, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = FIND_BY_EXISTING_USER, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByUserWhichExists() {
        User user = UserUtils.getListOfUser().get(3);
        System.out.println(user);

        Token token = tokenService.findByUser(userService.getUserByEmail("user@softserveinc.com"));
        System.out.println(token);

        Assert.assertEquals("abcd", tokenService.findByUser(userService.getUserByEmail("user@softserveinc.com")).getToken());
    }

}
