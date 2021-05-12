package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.entities.City;
import org.sber.bootcamp.cityinformer.util.CityComparatorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * Производит чтение из файла, если путь указан в качестве аргумента при запуске программы.
     * @param args путь к файлу
     */
    public static void main(String[] args) {
        List<City> cities = null;
        if(args.length>0) {
            if (Files.isReadable(Paths.get(args[0]))) {
                try {
                    cities = fileRead(Paths.get(args[0]));
                } catch (IOException e) {
                    Logger.getLogger("Main").log(Level.WARNING, "ОШИБКА ЧТЕНИЯ ФАЙЛА " + args[0], e);
                    System.exit(2);
                }
            } else {
                Logger.getLogger("Main").log(Level.WARNING, "Файл недоступен для чтения " + args[0]);
                System.exit(1);
            }
        }
        else {
            Scanner scanner = new Scanner(System.in);
            cities = consoleRead(scanner);
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
     * @param cities города
     * @return словарь, ключ - название региона, значение - количество городов в регионе
     */
    static Map<String, Integer> getCitiesByRegion(List<City> cities){
        HashMap<String, Integer> map = new HashMap<>();
        for(City city: cities){
            map.compute(city.getRegion(), (key,v) -> v==null?1:v+1);
        }
        return map;
    }


    /**
     * Ищет город с максимальным населением
     * @param cities города
     * @return массив с двумя элементами. Первый - индекс найденного города в массиве. 2 - население этого города.
     */
    static int[] findMaxPopulation(City[] cities){
        if(cities == null || cities.length == 0)
            throw new IllegalArgumentException("Передан пустой массив");
        int index = 0;
        int maxPop = cities[0].getPopulation();
        for(int i = 1; i < cities.length; i++){
            if(cities[i].getPopulation() > maxPop){
                maxPop = cities[i].getPopulation();
                index = i;
            }
        }
        return new int[]{index, maxPop};
    }

    /**
     * Чтение списка городов из консоли
     * @param scanner сканнер для чтения из потока ввода
     * @return список считанных городов
     */
    static List<City> consoleRead(Scanner scanner) {
        System.out.print("Число записей: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        ArrayList<City> cities = new ArrayList<>();
        while (count-- > 0) {
            try {
                cities.add(readCity(scanner.nextLine()));
            }catch (IOException e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод строки.");
                count++;
            }
        }
        return cities;
    }

    /**
     * Чтение списка городов из файла
     * @param path путь к файлу для чтения
     * @return список городов, считанных из файла
     */
    static List<City> fileRead(Path path) throws IOException {
        if(Files.isReadable(path)) {
            List<String> lines = Files.readAllLines(path);
            ArrayList<City> cities = new ArrayList<>();
            for (String line : lines) {
                cities.add(readCity(line));
            }
            return cities;
        }
        else
            throw new IllegalArgumentException("Файл недоступен для чтения или не существует");
    }


    /**
     * Десериализует объект City из строки
     * @param rawData строка для обработки
     * @return считанный объект City
     * @throws IOException если переданная строка в неправильном формате
     */
    static City readCity(String rawData) throws IOException {
        String[] data = rawData.split(";");
        if(data.length != 6)
            throw new IOException("Неформатная строка");
        String name = data[1];
        String region = data[2];
        String district = data[3];
        int population = Integer.parseInt(data[4]);
        LocalDate date = LocalDate.of(Integer.parseInt(data[5]), 1, 1);
        return new City(name, region, district, population, date);
    }
}
