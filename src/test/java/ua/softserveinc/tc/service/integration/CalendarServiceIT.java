package ua.softserveinc.tc.service.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.CalendarService;

import static ua.softserveinc.tc.paths.CalendarServiceITPath.NO_EVENT;
import static ua.softserveinc.tc.paths.CalendarServiceITPath.ONE_EVENT;
import static ua.softserveinc.tc.util.EventUtils.getListOfEvents;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class CalendarServiceIT    {

    @Autowired
    private CalendarService calendarService;

    @DatabaseSetup(value = NO_EVENT, type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = ONE_EVENT, assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = ONE_EVENT, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testAddIfNoEventsExists() {
        Event event = getListOfEvents().get(0);
        calendarService.add(event);
    }

    @DatabaseSetup(value = ONE_EVENT, type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = ONE_EVENT, assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = ONE_EVENT, type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testAddIfEventExist() {
        Event event = getListOfEvents().get(0);
        calendarService.add(event);
    }
}
