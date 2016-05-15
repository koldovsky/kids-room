package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import ua.softserveinc.tc.entity.City;
import ua.softserveinc.tc.constants.ColumnConstants.CityConst;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Repository
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void deleteCityById(Long id) {
        entityManager.createQuery("DELETE FROM City where id = " + id).executeUpdate();
    }

    @Override
    public City getCityByName(String name) {

        TypedQuery<City> query = getEntityManager().createNamedQuery("", City.class);

        return query.setParameter(CityConst.NAME_CITY, name).getSingleResult();
    }
}
