package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Rate;

import java.util.List;

public interface RateDao extends BaseDao<Rate> {

    public List<Rate> getRatesByRoomId(Long roomId);

}
