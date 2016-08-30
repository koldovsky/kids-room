package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.service.RateService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    public Long calculateBookingCost(Booking booking){
        List<Rate> rates = booking.getRoom().getRates();
        Rate closestRate = this.calculateAppropriateRate(booking.getDuration(), rates);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(booking.getDuration());
        double minuteCostInCoins = (double)(closestRate.getPriceRate()*100/(closestRate.getHourRate()*60));
        Long sum = (long)(minutes * minuteCostInCoins);
        return sum;
    }
}
