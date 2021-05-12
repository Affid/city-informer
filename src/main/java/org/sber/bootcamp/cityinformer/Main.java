package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String menu = "Меню:\n1 - Считать города из файла\n2 - Отсортировать по названию" +
            "\n3 - Отсортировать по федеральному округу\n4 - Вывести город с максимальным населением" +
            "\n5 - Вывести численность городов в регионах\n6 - Вывести города\n7 - Вывести это меню\n8 - Выйти";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CityDictionary cityDictionary = new CityDictionary();
        String command;
        System.out.println(menu);
        boolean stop = false;
        while (!stop) {
            System.out.print("Введите команду: ");
            while (!scanner.hasNextLine()) ;
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    System.out.println("Введите путь к файлу:");
                    command = scanner.nextLine();
                    try {
                        cityDictionary.readFile(command);
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Некорректный путь. Указанный файл не существует или не доступен для чтения");
                    }
                    System.out.println("Данные загружены.");
                    break;
                case "2":
                    cityDictionary.sortByName();
                    System.out.println("Сортировка завершена");
                    break;
                case "3":
                    cityDictionary.sortByDistrict();
                    System.out.println("Сортировка завершена");
                    break;
                case "4":
                    try {
                        Pair<Integer, Integer> pair = cityDictionary.findMaxPopulation();
                        System.out.printf("[%d] = %d\n", pair.getFirst(), pair.getSecond());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Сначала загрузите данные о городах.");
                    }
                    break;
                case "5":
                    Map<String, Integer> regions = cityDictionary.getCitiesByRegion();
                    if (regions.size() > 0) {
                        regions.forEach((k, v) -> System.out.println(k + " : " + v));
                    } else {
                        System.out.println("Сначала загрузите данные о городах.");
                    }
                    break;
                case "6":
                    cityDictionary.printCities();
                    break;
                case "7":
                    System.out.println(menu);
                    break;
                case "8":
                    stop = true;
                    break;
                default:
                    System.out.println("Команда отсутствует");
                    break;
            }
        }
        scanner.close();
    }
}
