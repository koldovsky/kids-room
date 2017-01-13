package ua.softserveinc.tc.dao.unit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.softserveinc.tc.categories.UnitTest;
import ua.softserveinc.tc.dao.impl.RateDaoImpl;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.util.RateUtils;

import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
@RunWith(JUnitParamsRunner.class)
public class RateDaoTest {

    @Mock
    private RateDaoImpl rateDao;

    @Before
    public void initialization() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters(method = "getListOfRates")
    public void testGetRatesByRoomId(List<Rate> listOfRate, Long roomId) {
        when(rateDao.getRatesByRoomId(roomId)).thenReturn(listOfRate);

        rateDao.getRatesByRoomId(roomId);

        verify(rateDao, times(1)).getRatesByRoomId(roomId);
    }

    private static final Object[] getListOfRates() {
        return $(
                $(RateUtils.getListOfRates(), 1L)
        );
    }

}
