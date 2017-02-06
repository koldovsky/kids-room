package ua.softserveinc.tc.service.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
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
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.util.DayOffUtils;
import ua.softserveinc.tc.util.RoomUtils;

import java.time.LocalDate;
import java.util.Set;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class DayOffServiceIT {

    @Autowired
    private DayOffService dayOffService;

    private LocalDate startDate;
    private LocalDate endDate;

    @Before()
    public void init() {
        startDate = LocalDate.of(2016, 10, 2);
        endDate = LocalDate.of(2016, 10, 2);
    }

    @DatabaseSetup(value = "classpath:dayOffService/create/some.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dayOffService/create/after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dayOffService/create/after.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testCreateDayOffEvent() {
        Set<Room> rooms = RoomUtils.getSetOfRooms();
        DayOff dayOff = DayOffUtils.createDayOff(1L, startDate, endDate, "Repairs works", rooms);
        dayOffService.createDayOffEvent(dayOff);
    }



}
