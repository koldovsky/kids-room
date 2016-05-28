package ua.softserveinc.tc.service;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.util.DateUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        final int hours = dateUtil.getRoundedHours(milliseconds);
        Optional<Rate> min = rates.stream()
            .filter(rate -> hours <= rate.getHourRate())
            .min(Comparator.comparing(Rate::getHourRate));

        if (min.isPresent()) return min.get();
        else return rates.stream().max(Comparator.comparing(Rate::getHourRate)).get();
    }
}
