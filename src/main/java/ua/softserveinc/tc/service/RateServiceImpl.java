package ua.softserveinc.tc.service;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RateServiceImpl extends BaseServiceImpl<Rate> implements RateService
{

    @Autowired
    private RateDao rateDao;

    public void create(Rate rate)
    {
        rateDao.create(rate);
    }

    @Override
    public int calculateClosestHour(long milliseconds, Map<Integer, Long> rates)
    {
        int hours = (int) TimeUnit.MILLISECONDS.toHours(milliseconds);
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.HOURS.toMillis(hours));

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        ArrayList<Integer> listOfKeys = new ArrayList<>();
        listOfKeys.addAll(rates.keySet());
        Collections.sort(listOfKeys);

        for (Integer hour : listOfKeys)
            if (hours <= hour) return hour;

        // if manager enters value, that is bigger, than max value in rates
        return listOfKeys.get(listOfKeys.size() - 1);
    }
}
