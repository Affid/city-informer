package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.util.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String basicMenu = "Меню:\n1 - Отсортировать по названию" +
            "\n2 - Отсортировать по федеральному округу\n3 - Вывести город с максимальным населением" +
            "\n4 - Вывести численность городов в регионах\n5 - Вывести города\n6 - Вывести это меню\n7 - Выйти";
    private static final String fileMenu = "\n8 - Загрузить другой файл";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CityDictionary cityDictionary = null;
        String command;
        while (cityDictionary == null) {
            System.out.println("Введите путь к файлу:");
            command = scanner.nextLine();
            try {
                cityDictionary = new FileCityDictionary(command);
                cityDictionary.readFile();
                System.out.println("Данные загружены.");
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла");
                cityDictionary = null;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный путь. Указанный файл не существует или не доступен для чтения");
                cityDictionary = null;
            }
        }
        System.out.println(basicMenu+fileMenu);
        boolean stop = false;
        while (!stop) {
            System.out.print("Введите команду: ");
            command = scanner.nextLine();
            switch (command) {
                case "8":
                    CityDictionary temp = cityDictionary;
                    System.out.println("Введите путь к файлу:");
                    command = scanner.nextLine();
                    try {
                        cityDictionary = new FileCityDictionary(command);
                        cityDictionary.readFile();
                        System.out.println("Данные загружены.");
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла");
                        cityDictionary = temp;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Некорректный путь. Указанный файл не существует или не доступен для чтения");
                        cityDictionary = temp;
                    }
                    break;
                case "1":
                    cityDictionary.sortByName();
                    System.out.println("Сортировка завершена");
                    break;
                case "2":
                    cityDictionary.sortByDistrict();
                    System.out.println("Сортировка завершена");
                    break;
                case "3":
                    try {
                        Pair<Integer, Integer> pair = cityDictionary.findMaxPopulation();
                        System.out.printf("[%d] = %d\n", pair.getFirst(), pair.getSecond());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Сначала загрузите данные о городах.");
                    }
                    break;
                case "4":
                    Map<String, Integer> regions = cityDictionary.getCitiesByRegion();
                    if (regions.size() > 0) {
                        regions.forEach((k, v) -> System.out.println(k + " : " + v));
                    } else {
                        System.out.println("Сначала загрузите данные о городах.");
                    }
                    break;
                case "5":
                    cityDictionary.printCities();
                    break;
                case "6":
                    System.out.println(basicMenu+fileMenu);
                    break;
                case "7":
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
