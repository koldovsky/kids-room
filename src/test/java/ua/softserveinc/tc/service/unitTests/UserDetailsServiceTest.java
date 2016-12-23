package ua.softserveinc.tc.service.unitTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.impl.UserDetailsServiceImpl;
import ua.softserveinc.tc.util.UserUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by melancholiya on 23.12.2016.
 */

public class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @Before
    public void beforeTests() {
        // todo: use BlockJUnit4ClassRunnerWithParameters
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadNullUserByUsername() {
        when(this.userDetailsService.loadUserByUsername("userEmail")).thenReturn(null);
        userDetailsService.loadUserByUsername("userEmail");
        verify(userDetailsService, times(1)).loadUserByUsername("someEmail");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testAnotherNullUserByUsername() {
        when(this.userService.getUserByEmail("someEmail")).thenReturn(null);
        userDetailsService.loadUserByUsername("someEmail");
        verify(userDetailsService, times(1)).loadUserByUsername("someEmail");
    }

    @Test
    public void testNotNullUserByUsername() {
        User user = UserUtils.getListOfUser().get(0);

        when(this.userService.getUserByEmail(user.getEmail())).thenReturn(user);

    }

}
