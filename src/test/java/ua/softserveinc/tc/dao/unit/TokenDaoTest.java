package ua.softserveinc.tc.dao.unit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.impl.TokenDaoImpl;
import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.messaging.TokenMessages;
import ua.softserveinc.tc.util.TokenUtils;
import ua.softserveinc.tc.util.UserUtils;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class TokenDaoTest {

    @Mock
    private TokenDaoImpl tokenDao;

    @Before
    public void initialization() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters(method = "getListOfUsers")
    public void testFindByUser(User user, Token token){
        when(tokenDao.findByUser(user)).thenReturn(token);

        tokenDao.findByUser(user);

        verify(tokenDao, times(1)).findByUser(user);
        Assert.assertEquals(TokenMessages.FIND_BY_TOKEN_ERROR, token, tokenDao.findByUser(user));
    }

    private static final Object[] getListOfUsers() {
        return $(
                $(UserUtils.getListOfUser().get(0), TokenUtils.getListOfTokens().get(0)),
                $(UserUtils.getListOfUser().get(1), TokenUtils.getListOfTokens().get(1))
        );
    }

}
