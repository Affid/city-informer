package org.sber.bootcamp.cityinformer.service;

import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CityDictionary {

    void update() throws IOException;

    void sortByName();

    void sortByDistrict();

    /**
     * Разбиение количества городов по регионам
     *
     * @return словарь, ключ - название региона, значение - количество городов в регионе
     */
    Map<String, Integer> getCitiesByRegion();


    /**
     * Ищет город с максимальным населением
     *
     * @return массив с двумя элементами. Первый - индекс найденного города в массиве. 2 - население этого города.
     */
    default Pair<Integer, Integer> findMaxPopulation(){
        City[] cities = getCities().toArray(new City[0]);
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

    List<City> getCities();

    void setCities(List<City> cities);
}
