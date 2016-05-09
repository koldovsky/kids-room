package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import ua.softserveinc.tc.entity.City;

/**
 * Created by Chak on 30.04.2016.
 */
@Repository
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao {

    //    @Override
//    public City getCityByName(String name) {
//
//        TypedQuery<City> query = getEntityManager().createNamedQuery(City.FIND_CITY_BY_NAME, City.class);
//
//        return query.setParameter(CityConst.NAME_CITY, name).getSingleResult();
//    }
}
