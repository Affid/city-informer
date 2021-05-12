package org.sber.bootcamp.cityinformer;

import org.junit.jupiter.api.*;
import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    private static String[] regions = new String[]{"Адыгея", "Хакасия", "Башкортостан",
            "Оренбургская область", "Татарстан", "Якутия", "Алтай", "Московская область"};


    @Test
    @Order(1)
    @DisplayName("Чтение городов из файла")
    void checkRead() {
        Path correctPath = Paths.get("src/test/resources/config1.txt");
        Path incorrectPath = Paths.get("src/test/resources/conf.txt");
        Path invalidDataPath = Paths.get("src/test/resources/config2.txt");
        Path invalidDataPath2 = Paths.get("src/test/resources/config3.txt");
        assertDoesNotThrow(() -> CityReader.fileRead(correctPath));
        assertThrows(IllegalArgumentException.class, () -> CityReader.fileRead(incorrectPath));
        assertThrows(IOException.class, () -> CityReader.fileRead(invalidDataPath), "Неформатная строка");
        assertThrows(NumberFormatException.class, () -> CityReader.fileRead(invalidDataPath2));
    }


    @Test
    @Order(2)
    @DisplayName("Сортировки")
    void checkSort() {
        List<City> cities = getRandomCities(6);
        cities.sort(CityComparatorFactory.byName());
        for (int i = 1; i < cities.size(); i++) {
            assertTrue(cities.get(i).getName().compareToIgnoreCase(cities.get(i - 1).getName()) < 0);
        }
        cities.sort(CityComparatorFactory.byDistrict());
        for (int i = 1; i < cities.size(); i++) {
            int res = cities.get(i).getDistrict().compareTo(cities.get(i - 1).getDistrict());
            assertFalse(res < 0);
            assertFalse(res == 0 && cities.get(i).getName().compareTo(cities.get(i - 1).getName()) < 0);
        }
    }


    @Test
    @Order(3)
    @DisplayName("Поиск максимального населения")
    void checkMaxPopulation() {
        List<City> cities = getRandomCities(10);
        cities.sort(CityComparatorFactory.byName());
        int[] maxPop = Main.findMaxPopulation(cities.toArray(new City[0]));
        for (City city : cities) {
            assertTrue(city.getPopulation() <= maxPop[1]);
        }
    }


    @Test
    @Order(4)
    @DisplayName("Разбиение городов по регионам")
    void checkCitiesInRegion() {
        int countPerRegion = 3;
        List<City> cities = getRandomCities(countPerRegion);
        cities.sort(CityComparatorFactory.byName());
        Map<String, Integer> regionsMap = Main.getCitiesByRegion(cities);
        assertEquals(regionsMap.size(), regions.length);
        for (int val : regionsMap.values()) {
            assertEquals(val, countPerRegion);
        }
    }

    /**
     * Генерирует список случайных городов в каждом регионе.
     *
     * @param countPerRegion количество городов в каждом регионе.
     * @return список городов
     */
    private static List<City> getRandomCities(int countPerRegion) {
        List<City> cities = new ArrayList<>();
        Random random = new Random();
        for (String region : regions) {
            for (int j = 0; j < countPerRegion; j++) {
                String name = getRandomString(random, 10);
                String district = getRandomString(random, 5);
                int population = 500 + random.nextInt(10_000_000);
                int year = 1830 + random.nextInt(190);
                cities.add(new City(name, region, district, population, LocalDate.of(year, 1, 1)));
            }
        }
        return cities;
    }

    /**
     * Генерирует случайную буквенную строку заданной длины
     *
     * @param len длина выходной строки
     * @return случайная срока
     */
    private static String getRandomString(Random random, int len) {
        int leftLimit = 'а';
        int rightLimit = 'я';
        int bigLeftLimit = 'А';
        int bigRightLimit = 'Я';
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (random.nextInt(2) == 1)
                generatedString.append((char) (leftLimit + random.nextInt(rightLimit + 1 - leftLimit)));
            else
                generatedString.append((char) (bigLeftLimit + random.nextInt(bigRightLimit + 1 - bigLeftLimit)));
        }
        return generatedString.toString();
    }
}
