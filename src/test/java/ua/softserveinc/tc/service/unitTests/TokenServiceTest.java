package ua.softserveinc.tc.service.unitTests;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.impl.TokenServiceImpl;
import ua.softserveinc.tc.util.TokenUtils;
import ua.softserveinc.tc.util.UserUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO: implement;

public class TokenServiceTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenDao tokenDao;

    @Test
    public void beforeTest()
    {
        // todo: use JParams;
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByToken()
    {
        when(tokenDao.findByToken("first token"))
                .thenReturn(TokenUtils.getListOfTokens().get(0));
    }

    @Test
    public void testFindByUser()
    {
        User user = UserUtils.getListOfUser().get(0);
        Token token = TokenUtils.getListOfTokens().get(0);
        System.out.println("token: " + token);
        System.out.println("user: " + user);

        when(tokenDao.findByUser(user)).thenReturn(token);

        verify(tokenDao, times(1)).findByUser(user);
    }


}
