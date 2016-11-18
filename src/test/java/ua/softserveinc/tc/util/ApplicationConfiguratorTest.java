package ua.softserveinc.tc.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nestor on 10.06.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class ApplicationConfiguratorTest {
    @Resource
    private ApplicationConfigurator appConfigurator;

    @Test
    public void testProperties() {
        assertEquals(3, (Object) appConfigurator.getKidsMinAge());
    }

    @Test
    public void testPropertiesMaxAge() {
        assertEquals(8,(Object) appConfigurator.getKidsMaxAge());
    }
    @Test
    public void testPropertiesMinPeriodSize(){
     assertEquals(15, (Object) appConfigurator.getMinPeriodSize());
    }
    @Test
    public void testPropertiesServerName()
    {
        assertEquals("kidroom.herokuapp.com", appConfigurator.getServerName());
    }
    @Test
    public void testPropertiesMaxUploadImgSizeMb()
    {
        assertEquals(10, (Object)appConfigurator.getMaxUploadImgSizeMb());
    }
}
