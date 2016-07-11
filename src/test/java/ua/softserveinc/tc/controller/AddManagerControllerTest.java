package ua.softserveinc.tc.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.config.WebAppConfig;
import ua.softserveinc.tc.controller.admin.AddManagerController;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.TokenService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.UserValidator;

import javax.mail.MessagingException;

/**
 * Created by TARAS on 02.07.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class AddManagerControllerTest {

    @InjectMocks
    private AddManagerController addManagerController;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserValidator userValidator;

    @Mock
    private User manager;

    @Mock
    private MailService mailService;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void showAddManagerFormTest() {
        AddManagerController controller = new AddManagerController();
        ModelAndView model = controller.showAddManagerForm();
        Assert.assertEquals("adm-add-manager", model.getViewName());
    }

    @Test
    public void saveNewManagerTest() throws MessagingException {
        Assert.assertNotNull(this.bindingResult);
        Assert.assertNotNull(this.userValidator);
        Assert.assertNotNull(this.manager);
        Assert.assertNotNull(this.userService);
        Assert.assertNotNull(this.tokenService);

        Mockito.doNothing().when(this.userValidator).validateManagerEmail(this.manager, this.bindingResult);
        Mockito.doNothing().when(this.mailService).buildConfirmRegisterManager(Mockito.eq("Confirmation registration")
                , Mockito.eq(this.manager), Mockito.any());
        Mockito.doNothing().when(this.userService).create(this.manager);
        Mockito.doNothing().when(this.tokenService).createToken(Mockito.any(), Mockito.eq(this.manager));

        this.addManagerController.saveNewManager(this.manager, this.bindingResult);

        Mockito.verify(this.userValidator).validateManagerEmail(this.manager, this.bindingResult);
        Mockito.verify(this.mailService).buildConfirmRegisterManager(Mockito.eq("Confirmation registration")
                , Mockito.eq(this.manager), Mockito.any());
        Mockito.verify(this.userService).create(this.manager);
        Mockito.verify(this.tokenService).createToken(Mockito.any(), Mockito.eq(this.manager));
    }
}
