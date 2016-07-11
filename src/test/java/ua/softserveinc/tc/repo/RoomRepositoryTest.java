package ua.softserveinc.tc.repo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Room;

/**
 * Created by TARAS on 07.07.2016.
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class RoomRepositoryTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Room room;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findOneTest(){
        Assert.assertNotNull(this.room);
        Assert.assertNotNull(this.roomRepository);
        Long id = 1L;

        Mockito.when(this.roomRepository.findOne(id)).thenReturn(this.room);
        Mockito.verify(this.roomRepository.findOne(id));

        Assert.assertEquals(this.room, this.roomRepository.findOne(id));
    }

}
