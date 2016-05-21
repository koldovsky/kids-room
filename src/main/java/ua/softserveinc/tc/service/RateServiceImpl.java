package ua.softserveinc.tc.service;

/**
 * Created by TARAS on 19.05.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;

@Service
public class RateServiceImpl extends BaseServiceImpl<Rate> implements RateService {

    @Autowired
    private RateDao rateDao;

    public void create(Rate rate) {
        rateDao.create(rate);
    }


}
