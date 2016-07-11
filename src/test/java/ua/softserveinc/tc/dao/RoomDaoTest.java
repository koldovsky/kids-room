package ua.softserveinc.tc.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.config.WebAppConfig;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.impl.RoomServiceImpl;

import java.util.List;

/**
 * Created by TARAS on 07.07.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class RoomDaoTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomDao roomDao;

    @Mock
    private Room room;

    @Mock
    private List<Room> roomList;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findByIdTest() {
        Assert.assertNotNull(this.room);
        Assert.assertNotNull(this.roomDao);
        Long id = 1L;

        Mockito.when(this.roomDao.findById(id)).thenReturn(this.room);
        Mockito.verify(this.roomDao.findById(id));
        Assert.assertEquals(this.room, this.roomDao.findById(id));
    }

    @Test
    @Rollback(true)
    public void saveOrUpdateTest() {
        Assert.assertNotNull(this.room);
        Assert.assertNotNull(this.roomDao);

        Mockito.doNothing().when(this.roomDao).saveOrUpdate(this.room);
        this.roomService.saveOrUpdate(this.room);
        Mockito.verify(this.roomDao).saveOrUpdate(this.room);
    }

    @Test
    public void findAllTest(){
        Assert.assertNotNull(this.roomService);
        Assert.assertNotNull(this.roomDao);
        Assert.assertNotNull(this.roomList);

        Mockito.when(this.roomDao.findAll()).thenReturn(this.roomList);
        Assert.assertEquals(this.roomService.findAll(), this.roomList);
        Mockito.verify(this.roomDao).findAll();
    }
}