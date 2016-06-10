package util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.util.ApplicationConfigurator;

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
    public void testProperties(){
        assertEquals(3, (Object) appConfigurator.getKidsMinAge());
    }
}
