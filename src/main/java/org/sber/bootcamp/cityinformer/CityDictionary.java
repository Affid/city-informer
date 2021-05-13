package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CityDictionary {

    void printCities();

    void readFile() throws IOException;

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
    Pair<Integer, Integer> findMaxPopulation();

    List<City> getCities();

    void setCities(List<City> cities);
}
