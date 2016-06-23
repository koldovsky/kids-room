package ua.softserveinc.tc.service.impl;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.service.RateService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static ua.softserveinc.tc.util.DateUtil.getRoundedHours;

@Service
public class RateServiceImpl extends BaseServiceImpl<Rate> implements RateService {
    @Override
    public Rate calculateAppropriateRate(long milliseconds, List<Rate> rates) {
        if (rates.isEmpty()) {
            return Rate.DEFAULT;
        }

        int hours = getRoundedHours(milliseconds);
        Optional<Rate> min = rates.stream()
                .filter(rate -> hours <= rate.getHourRate())
                .min(Comparator.comparing(Rate::getHourRate));

        if (min.isPresent()) {
            return min.get();
        } else {
            return Collections.max(rates, Comparator.comparing(Rate::getHourRate));
        }
    }
}