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
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.EventService;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class EventRepositoryTestIT {

    @Autowired
    private EventService eventRepository;

    @DatabaseSetup(value = "classpath:eventRepository/no-events.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:eventRepository/no-events.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByNameIfThereIsNoEvent() {
        List<Event> eventList = eventRepository.findByName("New Year event");
        assertTrue(eventList.isEmpty());
    }

    @DatabaseSetup(value = "classpath:eventRepository/multiple-events.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:eventRepository/multiple-events.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByNameIfThereAreMultipleEvents() {
        List<Event> eventList = eventRepository.findByName("Serious event");
        assertFalse(eventList.isEmpty());
    }

}
