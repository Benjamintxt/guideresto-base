package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.persistence.CityMapper;

import java.util.ArrayList;

public class CityService {

    public ArrayList<City> getAllCities(){
        CityMapper cityMapper = CityMapper.getInstance();
        return cityMapper.findAll();
    }

    public City insert(City city){
        CityMapper cityMapper = CityMapper.getInstance();
        cityMapper.insert(city);
        return city;
    }

    public City update(City city) {
        CityMapper cityMapper = CityMapper.getInstance();
        cityMapper.update(city);
        return city;
    }
    public City delete(City city) {
        CityMapper cityMapper = CityMapper.getInstance();
        cityMapper.delete(city);
        return city;
    }

}
