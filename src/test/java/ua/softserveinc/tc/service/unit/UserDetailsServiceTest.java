package ua.softserveinc.tc.service.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.service.impl.UserDetailsServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @Before
    public void beforeTests() {
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


}
