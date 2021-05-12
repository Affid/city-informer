package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.entities.City;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class MainTest {

    private static String[] regions = new String[]{"Адыгея", "Хакасия", "Башкортостан",
            "Оренбургская область", "Татарстан", "Якутия", "Алтай", "Московская область"};

    /**
     * Проводит все тесты.
     */
    public static void main(String[] args) {
        boolean ok = checkRead();
        System.out.println("Чтение: " + (ok ? "✓" : "x"));
        if (ok) {
            ok = checkSort();
            System.out.println("Сортировка: " + (ok ? "✓" : "x"));
            if (ok) {
                ok = checkMaxPopulation();
                System.out.println("Поиск крупнейшего города: " + (ok ? "✓" : "x"));
                if (ok) {
                    ok = checkCitiesInRegion();
                    System.out.println("Подсчет городов в регионах: " + (ok ? "✓" : "x"));
                }
            }
        }
    }

    /**
     * Тест чтения из файла.
     * @return {@code true}, если тест пройден
     */
    public static boolean checkRead() {
        Path correctPath = Paths.get("src/test/resources/config1.txt");
        Path incorrectPath = Paths.get("src/test/resources/conf.txt");
        Path invalidDataPath = Paths.get("src/test/resources/config2.txt");
        Path invalidDataPath2 = Paths.get("src/test/resources/config3.txt");
        boolean result;
        try { //чтение корректного файла
            Main.fileRead(correctPath);
            result = true;
        } catch (IOException e) {
            result = false;
        }
        if (!result)
            return false;
        try { //чтение по неправильному пути
            Main.fileRead(incorrectPath);
            result = false;
        } catch (IOException e) {
            result = false;
        } catch (IllegalArgumentException e) { //отлавливаем ошибку, сообщающую, что путь некорректен
            result = true;
        }
        if (!result)
            return false;
        try {//читаем файл с неформатной строкой
            Main.fileRead(invalidDataPath);
            result = false;
        } catch (IOException e) {
            result = "Неформатная строка".equals(e.getMessage());
        }
        if (!result)
            return false;
        try { //читаем файл с некорректными данными - строки вместо чисел
            Main.fileRead(invalidDataPath2);
            result = false;
        } catch (NumberFormatException e) {
            result = true;
        } catch (IOException e) {
            result = false;
        }

        return result;
    }

    /**
     * Тест сортировки.
     * @return {@code true}, если тест пройден
     */
    public static boolean checkSort() {
        try {
            List<City> cities = Main.fileRead(Paths.get("src/test/resources/config1.txt"));
            cities.sort(CityComparatorFactory.byName());
            for (int i = 1; i < cities.size(); i++) {
                if (cities.get(i).getName().toLowerCase(Locale.ROOT).compareTo(cities.get(i - 1).getName().toLowerCase(Locale.ROOT)) < 0)
                    return false;
            }
            cities.sort(CityComparatorFactory.byDistrict());
            for (int i = 1; i < cities.size(); i++) {
                int res = cities.get(i).getDistrict().compareTo(cities.get(i - 1).getDistrict());
                if (res < 0)
                    return false;
                else if (res == 0 && cities.get(i).getName().compareTo(cities.get(i - 1).getName()) < 0)
                    return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Тест поиска максимального населения.
     * @return {@code true}, если тест пройден
     */
    public static boolean checkMaxPopulation() {
        try {
            List<City> cities = Main.fileRead(Paths.get("src/test/resources/config1.txt"));
            cities.sort(CityComparatorFactory.byName());
            int[] maxPop = Main.findMaxPopulation(cities.toArray(new City[0]));
            return (maxPop[0] == 1 && maxPop[1] == 165183);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Тест разбиения городов по регионам
     * @return {@code true}, если тест пройден
     */
    public static boolean checkCitiesInRegion() {
        int countPerRegion = 3;
        List<City> cities = getRandomCities(countPerRegion);
        Map<String, Integer> regionsMap = Main.getCitiesByRegion(cities);
        if (regionsMap.size() != regions.length)
            return false;
        for (int val : regionsMap.values()) {
            if (val != countPerRegion)
                return false;
        }
        return true;
    }

    /**
     * Генерирует список случайных городов в каждом регионе.
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
     * @param len длина выходной строки
     * @return случайная срока
     */
    private static String getRandomString(Random random, int len) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
