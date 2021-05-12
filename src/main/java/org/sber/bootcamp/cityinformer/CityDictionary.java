package org.sber.bootcamp.cityinformer;


import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;
import org.sber.bootcamp.cityinformer.util.CityReader;
import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class CityDictionary {
    private List<City> cities;

    public CityDictionary() {
        this.cities = new ArrayList<>();
    }

    public void printCities() {
        cities.forEach(System.out::println);
    }

    public void readFile(String path) throws IOException {
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
    Map<String, Integer> getCitiesByRegion() {
        HashMap<String, Integer> map = new HashMap<>();
        for (City city : cities) {
            map.compute(city.getRegion(), (key, v) -> v == null ? 1 : v + 1);
        }
        return map;
    }


    /**
     * Ищет город с максимальным населением
     *
     * @return массив с двумя элементами. Первый - индекс найденного города в массиве. 2 - население этого города.
     */
    Pair<Integer, Integer> findMaxPopulation() {
        City[] cities = this.cities.toArray(new City[0]);
        if (cities.length == 0)
            throw new IllegalArgumentException("Передан пустой массив");
        int index = 0;
        int maxPop = cities[0].getPopulation();
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].getPopulation() > maxPop) {
                maxPop = cities[i].getPopulation();
                index = i;
            }
        }
        return new Pair<>(index, maxPop);
    }


}
