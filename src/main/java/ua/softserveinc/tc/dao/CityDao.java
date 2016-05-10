package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.City;

public interface CityDao extends BaseDao<City> {
        public City getCityByName(String name) ;

}
