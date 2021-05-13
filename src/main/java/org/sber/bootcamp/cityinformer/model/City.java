package org.sber.bootcamp.cityinformer.model;

import java.time.LocalDate;
import java.util.Objects;

public class City {
    private final int id;
    private final String name;
    private final String region;
    private final String district;
    private final int population;
    private final LocalDate date;

    public City(int id, String name, String region, String district, int population, LocalDate date) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", date=" + date.getYear() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return population == city.population && name.equals(city.name) && region.equals(city.region) && district.equals(city.district) && date.equals(city.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region, district, population, date);
    }
}
