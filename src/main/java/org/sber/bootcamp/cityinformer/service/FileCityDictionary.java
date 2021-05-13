package org.sber.bootcamp.cityinformer.service;


import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.service.CityDictionary;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;
import org.sber.bootcamp.cityinformer.util.CityReader;
import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileCityDictionary implements CityDictionary {
    private final String path;
    private List<City> cities;

    public FileCityDictionary(String path) {
        this.path = path;
        this.cities = new ArrayList<>();
    }

    public void update() throws IOException {
        cities = CityReader.fileRead(Paths.get(path));
    }

    public void sortByName() {
        cities.sort(CityComparatorFactory.byName());
    }

    public void sortByDistrict() {
        cities.sort(CityComparatorFactory.byDistrict());
    }

    /**
     * Разбиение количества городов по регионам
     *
     * @return словарь, ключ - название региона, значение - количество городов в регионе
     */
    public Map<String, Integer> getCitiesByRegion() {
        HashMap<String, Integer> map = new HashMap<>();
        for (City city : cities) {
            map.compute(city.getRegion(), (key, v) -> v == null ? 1 : v + 1);
        }
        return map;
    }

    public List<City> getCities() {
        return new ArrayList<>(cities);
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
