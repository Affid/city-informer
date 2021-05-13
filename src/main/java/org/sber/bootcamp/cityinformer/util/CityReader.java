package org.sber.bootcamp.cityinformer.util;

import org.sber.bootcamp.cityinformer.model.City;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityReader {
    /**
     * Чтение списка городов из файла
     *
     * @param path путь к файлу для чтения
     * @return список городов, считанных из файла
     */
    public static List<City> fileRead(Path path) throws IOException {
        if (Files.isReadable(path)) {
            List<String> lines = Files.readAllLines(path);
            ArrayList<City> cities = new ArrayList<>();
            for (String line : lines) {
                cities.add(readCity(line));
            }
            return cities;
        } else
            throw new IllegalArgumentException("Файл недоступен для чтения или не существует");
    }

    /**
     * Создает объект City из строки
     *
     * @param rawData строка для обработки
     * @return считанный объект City
     * @throws IOException если переданная строка в неправильном формате
     */
    static City readCity(String rawData) throws IOException {
        String[] data = rawData.split(";");
        if (data.length != 6) {
            throw new IOException("Неформатная строка");
        }
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String region = data[2];
        String district = data[3];
        int population = Integer.parseInt(data[4]);
        LocalDate date = LocalDate.of(Integer.parseInt(data[5]), 1, 1);
        return new City(id, name, region, district, population, date);
    }

    /**
     * Чтение списка городов из консоли
     *
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
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод строки.");
                count++;
            }
        }
        return cities;
    }
}
