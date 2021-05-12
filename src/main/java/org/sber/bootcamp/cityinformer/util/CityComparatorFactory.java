package org.sber.bootcamp.cityinformer.util;

import org.sber.bootcamp.cityinformer.model.City;

import java.util.Comparator;

public class CityComparatorFactory {

    public static Comparator<City> byName(){
        return (o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName());
    }

    public static Comparator<City> byDistrict(){
        return Comparator.comparing(City::getDistrict).thenComparing(City::getName);
    }
}
