package ua.softserveinc.tc.service.unitTests;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.dao.TokenDao;
import ua.softserveinc.tc.service.impl.TokenServiceImpl;
import ua.softserveinc.tc.util.TokenUtils;

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
        Mockito.when(tokenDao.findByToken("first token")).thenReturn(TokenUtils.getListOfTokens().get(0));
    }



}
