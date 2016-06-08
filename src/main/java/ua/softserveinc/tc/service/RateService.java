package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Rate;

import java.util.List;

/**
 * Created by TARAS on 19.05.2016.
 */
public interface RateService extends BaseService<Rate> {
    Rate calculateAppropriateRate(long milliseconds, final List<Rate> rates);
}
