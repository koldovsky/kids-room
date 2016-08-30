package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Rate;

import java.util.List;

public interface RateService extends BaseService<Rate> {
    Rate calculateAppropriateRate(long milliseconds, final List<Rate> rates);
    public Long calculateBookingCost(Booking booking);
}
