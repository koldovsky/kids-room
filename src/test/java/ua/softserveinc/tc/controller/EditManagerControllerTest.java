package ua.softserveinc.tc.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.constants.AdminConstants;
import ua.softserveinc.tc.controller.admin.EditManagerController;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class EditManagerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EditManagerController editManagerController;

    @Mock
    private UserService userService;

    @Mock
    private User manager;

    @Mock
    private List<User> managerList;

    @Spy
    private ModelAndView modelAndView;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        this.modelAndView.setViewName(AdminConstants.EDIT_MANAGER);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.editManagerController).build();
    }


    @Test
    public void showAllManagersFormTest() throws Exception {
        Assert.assertNotNull(this.userService);
        Assert.assertNotNull(this.modelAndView);
        Assert.assertNotNull(this.managerList);

        Mockito.doNothing().when(this.modelAndView).setViewName(AdminConstants.EDIT_MANAGER);
        Mockito.when(this.userService.findAllUsersByRole(Role.MANAGER)).thenReturn(this.managerList);
        Mockito.when(this.modelAndView.addObject(AdminConstants.MANAGER_LIST, this.managerList))
                .thenReturn(this.modelAndView);

        this.editManagerController.showAllManagersForm();

        Mockito.verify(this.userService).findAllUsersByRole(Role.MANAGER);
        Mockito.verify(this.modelAndView).addObject(AdminConstants.MANAGER_LIST, this.managerList);

        this.mockMvc.perform(MockMvcRequestBuilders.get(AdminConstants.EDIT_MANAGER)).andReturn();
        Assert.assertEquals(this.modelAndView, this.modelAndView.addObject(AdminConstants.MANAGER_LIST,
                this.managerList));
        Assert.assertEquals(AdminConstants.EDIT_MANAGER, this.modelAndView.getViewName());
    }

    @Test
    public void managerBlockUnblockTest() throws Exception {
        Assert.assertNotNull(this.userService);
        Assert.assertNotNull(this.manager);
        Long id = 1L;

        Mockito.when(this.userService.findById(id)).thenReturn(this.manager);
        Mockito.doNothing().when(this.manager).setActive(Mockito.anyBoolean());
        Mockito.when(this.userService.update(this.manager)).thenReturn(this.manager);

        String result = this.editManagerController.managerBlockUnblock(id);

        Mockito.verify(this.userService).findById(id);
        Mockito.verify(this.manager).setActive(Mockito.anyBoolean());
        Mockito.verify(this.userService).update(this.manager);

        this.mockMvc.perform(MockMvcRequestBuilders.post("redirect:/" + AdminConstants.EDIT_MANAGER));
        Assert.assertEquals(AdminConstants.EDIT_MANAGER, this.modelAndView.getViewName());
        Assert.assertEquals("redirect:/" + AdminConstants.EDIT_MANAGER, result);
    }
}