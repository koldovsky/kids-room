package ua.softserveinc.tc.services.integration;

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
import ua.softserveinc.tc.repo.DayOffRepository;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.util.DayOffUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

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

    @DatabaseSetup(value = "classpath:dayOffService/no-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dayOffService/one-dayoff.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dayOffService/one-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testUpdateWhenThereIsOnlyOneDayOff() {

        DayOff dayOff = DayOffUtils.createDayOff(1L, startDate, endDate, "Teachers day");
        dayOffService.update(dayOff);
    }

    @DatabaseSetup(value = "classpath:dayOffService/update-before-multiple-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dayOffService/update-after-multiple-dayoffs.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dayOffService/update-after-multiple-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testUpdateWhenThereAreMultipleDayOffs() {

        DayOff dayOff = DayOffUtils.createDayOff(2L, startDate, endDate, "Day Offs", new HashSet<>());
        dayOffService.update(dayOff);
    }

    @DatabaseSetup(value = "classpath:dayOffService/no-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dayOffService/no-dayoffs.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dayOffService/no-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testUpdateWhenThereIsNoDayOffs() {
        DayOff dayOff = DayOffUtils.createDayOff(1L, startDate, endDate, "Teachers day", new HashSet<>());
        dayOffService.update(dayOff);
    }

    @DatabaseSetup(value = "classpath:dayOffService/no-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffService/no-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByIdIfThereIsNoDayOffs() {
        assertNull(dayOffService.findById(1L));
    }

    @DatabaseSetup(value = "classpath:dayOffService/one-dayoff.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffService/one-dayoff.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByIdIfThereIsOneDayOffs() {
        assertNotNull(dayOffService.findById(1L));
        assertEquals("Teachers day", dayOffService.findById(1L).getName());
        LocalDate startDate = LocalDate.of(2016, 10, 2);
        assertEquals(startDate, dayOffService.findById(1L).getStartDate());
        LocalDate endDate = LocalDate.of(2016, 10, 2);
        assertEquals(endDate, dayOffService.findById(1L).getEndDate());
    }

    @DatabaseSetup(value = "classpath:dayOffService/one-dayoff.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffService/one-dayoff.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindByIdDayOffWhichIdNotExistsIfThereIsOneDayOff() {
        assertNull(dayOffService.findById(2L));
    }



}
