package ua.softserveinc.tc.repo;

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
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.util.DayOffUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBaseConfigClass.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class DayOffRepositoryTestIT {

    @Autowired
    private DayOffService dayOffRepository;

    private LocalDate startDate;
    private LocalDate endDate;

    @Before()
    public void init() {
        startDate = LocalDate.of(2016, 10, 2);
        endDate = LocalDate.of(2016, 10, 2);
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/no-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/no-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindOneIfThereIsNoDayOffs() {
        assertNull(dayOffRepository.findById(1L));
    }

    @DatabaseSetup(value = "classpath:dayOffService/update-before-multiple-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "classpath:dayOffService/update-after-multiple-dayoffs.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dayOffService/update-after-multiple-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testUpdateWhenThereAreMultipleDayOffs() {

        DayOff dayOff = DayOffUtils.createDayOff(2L, startDate, endDate, "Day Offs", new HashSet<>());
        dayOffRepository.update(dayOff);
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindOneIfThereIsOneDayOffs() {
        assertNotNull(dayOffRepository.findById(1L));
        assertEquals("Teachers day", dayOffRepository.findById(1L).getName());
        LocalDate startDate = LocalDate.of(2016, 10, 2);
        assertEquals(startDate, dayOffRepository.findById(1L).getStartDate());
        LocalDate endDate = LocalDate.of(2016, 10, 2);
        assertEquals(endDate, dayOffRepository.findById(1L).getEndDate());
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindOneDayOffWhichIdNotExistsIfThereIsOneDayOff() {
        assertNull(dayOffRepository.findById(2L));
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/multiple-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/multiple-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindOneIfThereAreMultipleDayOffs() {
        assertNotNull(dayOffRepository.findById(1L));
        assertNotNull(dayOffRepository.findById(2L));
        assertNull(dayOffRepository.findById(4L));
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/no-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/no-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllWhenThereAreNoDayOffs() {
        assertTrue(dayOffRepository.findAll().isEmpty());
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/one-dayoff.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllWhenThereAreOneDayOff() {
        List<DayOff> dayOffs = dayOffRepository.findAll();
        assertNotNull(dayOffs);
        assertEquals(1, dayOffs.size());
    }

    @DatabaseSetup(value = "classpath:dayOffRepository/multiple-dayoffs.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "classpath:dayOffRepository/multiple-dayoffs.xml", type = DatabaseOperation.DELETE_ALL)
    @Test
    public void testFindAllWhenThereAreMultipleDayOffs() {
        List<DayOff> dayOffs = dayOffRepository.findAll();
        assertNotNull(dayOffs);
        assertEquals(3, dayOffRepository.findAll().size());
    }



}
