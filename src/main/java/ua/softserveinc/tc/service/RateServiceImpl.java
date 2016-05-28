package ua.softserveinc.tc.service;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Collections;
import java.util.List;

@Service
public class RateServiceImpl extends BaseServiceImpl<Rate> implements RateService
{
    @Autowired
    private RateDao rateDao;

    @Autowired
    private DateUtil dateUtil;

    public void create(Rate rate)
    {
        rateDao.create(rate);
    }

    @Override
    public Rate calculateClosestRate(long milliseconds, final List<Rate> rates)
    {
        int hours = dateUtil.getHoursFromMilliseconds(milliseconds);
        int minutes = dateUtil.getMinutesFromMilliseconds(milliseconds);

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        Collections.sort(rates, (x, y) -> Integer.compare(x.getHourRate(), y.getHourRate()));

        for (Rate rate : rates)
            if (hours <= rate.getHourRate()) return rate;

        // if manager enters value, that is bigger, than max value in rates
        return rates.get(rates.size() - 1);
    }
}
