package org.sber.bootcamp.cityinformer.entities;

import java.time.LocalDate;

public class City {
    private String name;
    private String region;
    private String district;
    private int population;
    private LocalDate date;

    public City(String name, String region, String district, int population, LocalDate date) {
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
}
