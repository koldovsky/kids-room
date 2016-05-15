package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.softserveinc.tc.dao.CityDao;
import ua.softserveinc.tc.entity.City;

/**
 * Created by TARAS on 08.05.2016.
 */

@Service
public class CityServiceImpl extends BaseServiceImpl<City> implements CityService{

    @Autowired
    private CityDao cityDao;

    @Override
    public void deleteCityById(Long id){
        cityDao.deleteCityById(id);
    }

    @Override
    public City getCityByName(String name) {
        return cityDao.getCityByName(name);
    }

    @Override
    public void create(City city) {
        cityDao.create(city);
    }
}
