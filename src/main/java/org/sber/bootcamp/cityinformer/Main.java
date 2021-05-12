package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.model.City;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * Производит чтение из файла, если путь указан в качестве аргумента при запуске программы.
     *
     * @param args путь к файлу
     */
    public static void main(String[] args) {
        List<City> cities = null;
        if (args.length > 0) {
            if (Files.isReadable(Paths.get(args[0]))) {
                try {
                    cities = CityReader.fileRead(Paths.get(args[0]));
                } catch (IOException e) {
                    Logger.getLogger("Main").log(Level.WARNING, "ОШИБКА ЧТЕНИЯ ФАЙЛА " + args[0], e);
                    System.exit(2);
                }
            } else {
                Logger.getLogger("Main").log(Level.WARNING, "Файл недоступен для чтения " + args[0]);
                System.exit(1);
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            cities = CityReader.consoleRead(scanner);
        }
        System.out.println("Несортированный список городов:\n");
        cities.forEach(System.out::println);
        cities.sort(CityComparatorFactory.byName());
        System.out.println("\nСортировка по названию:");
        cities.forEach(System.out::println);
        cities.sort(CityComparatorFactory.byDistrict());
        System.out.println("\nСортировка по федеральному округу:");
        cities.forEach(System.out::println);

        int[] maxPopulation = findMaxPopulation(cities.toArray(new City[0]));
        System.out.printf("\nМаксимальное население:\n\t[%d] = %d\n", maxPopulation[0], maxPopulation[1]);
        System.out.println("Города в регионах:");
        System.out.println(getCitiesByRegion(cities));
    }

    /**
     * Разбиение количества городов по регионам
     *
     * @param cities города
     * @return словарь, ключ - название региона, значение - количество городов в регионе
     */
    static Map<String, Integer> getCitiesByRegion(List<City> cities) {
        HashMap<String, Integer> map = new HashMap<>();
        for (City city : cities) {
            map.compute(city.getRegion(), (key, v) -> v == null ? 1 : v + 1);
        }
        return map;
    }


    /**
     * Ищет город с максимальным населением
     *
     * @param cities города
     * @return массив с двумя элементами. Первый - индекс найденного города в массиве. 2 - население этого города.
     */
    static int[] findMaxPopulation(City[] cities) {
        if (cities == null || cities.length == 0)
            throw new IllegalArgumentException("Передан пустой массив");
        int index = 0;
        int maxPop = cities[0].getPopulation();
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].getPopulation() > maxPop) {
                maxPop = cities[i].getPopulation();
                index = i;
            }
        }
        return new int[]{index, maxPop};
    }


}
