package ua.softserveinc.tc.dao.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import junitparams.JUnitParamsRunner;
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

    @DatabaseSetup(value = "classpath:eventDao/no-event.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:eventDao/no-event.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetMaxRecurrentIdIfThereIsNoEvent() {
        Assert.assertEquals(Long.valueOf(0), eventDao.getMaxRecurrentId());
    }

    @DatabaseSetup(value = "classpath:eventDao/events.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:eventDao/events.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testGetRecurrentEventByRecurrentIdIfThereAreMultipleEvents() {
        Assert.assertEquals(Long.valueOf(1), eventDao.getRecurrentEventByRecurrentId(1L).get(0).getId());
    }


}
