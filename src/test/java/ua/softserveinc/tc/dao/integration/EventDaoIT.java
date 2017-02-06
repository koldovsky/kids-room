package ua.softserveinc.tc.dao.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
import ua.softserveinc.tc.dao.EventDao;

import static ua.softserveinc.tc.paths.EventDaoITPath.MULTIPLE_EVENTS;
import static ua.softserveinc.tc.paths.EventDaoITPath.NO_EVENT;
import static ua.softserveinc.tc.paths.EventDaoITPath.ONE_EVENT;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class EventDaoIT {

    @Autowired
    private EventDao eventDao;

    @DatabaseSetup(value = NO_EVENT, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = NO_EVENT, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetMaxRecurrentIdIfThereIsNoEvent() {
        Assert.assertEquals(Long.valueOf(0), eventDao.getMaxRecurrentId());
    }

    @DatabaseSetup(value = MULTIPLE_EVENTS, type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = MULTIPLE_EVENTS, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetRecurrentEventByRecurrentIdIfThereAreMultipleEvents() {
        Assert.assertEquals(Long.valueOf(1), eventDao.getRecurrentEventByRecurrentId(1L).get(0).getId());
    }

    @DatabaseSetup(value = ONE_EVENT, type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = NO_EVENT, assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = NO_EVENT, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testDeleteByRecurrentId() {
        eventDao.deleteByRecurrentId(1L);
    }

}

