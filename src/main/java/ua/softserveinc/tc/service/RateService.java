package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Rate;

import java.util.Map;

/**
 * Created by TARAS on 19.05.2016.
 */
public interface RateService extends BaseService<Rate>
{
    int calculateClosestHour(long milliseconds, Map<Integer, Double> rates);
}
