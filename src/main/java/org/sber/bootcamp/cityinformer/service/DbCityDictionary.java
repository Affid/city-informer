package org.sber.bootcamp.cityinformer.service;

import org.sber.bootcamp.cityinformer.dao.CityDao;
import org.sber.bootcamp.cityinformer.model.City;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbCityDictionary implements CityDictionary{
    private List<City> cities;
    private CityDao dao;

    public DbCityDictionary() throws SQLException{
        try {
            cities = new ArrayList<>();
            dao = CityDao.CityDaoKeeper.getInstance();
        }catch (IOException e){
            throw new IllegalStateException("Неверная конфигурация database.properties");
        }
    }

    @Override
    public void update(){
        cities = dao.all();
    }

    @Override
    public void sortByName() {
        cities = dao.sortByName();
    }

    @Override
    public void sortByDistrict() {
        cities = dao.sortByDistrictAndName();
    }

    @Override
    public Map<String, Integer> getCitiesByRegion() {
        return dao.groupCitiesByRegion();
    }

    @Override
    public List<City> getCities() {
        return new ArrayList<>(cities);
    }

    @Override
    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
