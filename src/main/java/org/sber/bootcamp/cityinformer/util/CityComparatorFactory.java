package org.sber.bootcamp.cityinformer.util;

import org.sber.bootcamp.cityinformer.entities.City;

import java.util.Comparator;
import java.util.Locale;

public class CityComparatorFactory {

    public static Comparator<City> byName(){
        return Comparator.comparing(o -> o.getName().toLowerCase(Locale.ROOT));
    }

    public static Comparator<City> byDistrict(){
        return Comparator.comparing(City::getDistrict).thenComparing(City::getName);
    }
}
