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
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.messaging.TokenMessages;

import static ua.softserveinc.tc.dao.paths.TokenDaoITPath.ONE_TOKEN;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class TokenDaoIT {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @DatabaseSetup(value = ONE_TOKEN, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = ONE_TOKEN, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByUser() {
        User user = userDao.getUserByEmail("user@softserveinc.com");
        Assert.assertEquals(TokenMessages.FIND_BY_USER_ERROR, "abcd", tokenDao.findByUser(user).getToken());
    }

    @DatabaseSetup(value = ONE_TOKEN, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = ONE_TOKEN, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByToken() {
        User user = userDao.getUserByEmail("user@softserveinc.com");
        Assert.assertEquals(TokenMessages.FIND_BY_TOKEN_ERROR, Long.valueOf(1L),
                tokenDao.findByToken("abcd").getId());

        Assert.assertEquals(TokenMessages.FIND_BY_TOKEN_ERROR, user.getFirstName(),
                tokenDao.findByToken("abcd").getUser().getFirstName());
    }

}
