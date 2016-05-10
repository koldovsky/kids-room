package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.entity.ColumnConstants.CityConst;

import javax.persistence.TypedQuery;


@Repository
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao {

    @Override
    public City getCityByName(String name) {

        TypedQuery<City> query = getEntityManager().createNamedQuery("", City.class);

        return query.setParameter(CityConst.NAME_CITY, name).getSingleResult();
    }
}
