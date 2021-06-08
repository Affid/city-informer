package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.service.CityDictionary;
import org.sber.bootcamp.cityinformer.service.DbCityDictionary;
import org.sber.bootcamp.cityinformer.service.FileCityDictionary;
import org.sber.bootcamp.cityinformer.util.Pair;
import org.sber.bootcamp.cityinformer.util.State;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private static final String basicMenu = String.join("\n", Collections.nCopies(50, "\n\r")) +
            String.join("\n", "Меню:",
                    "1 - База данных",
                    "2 - Загрузить свой файл",
                    "3 - Вывести это меню",
                    "4 - Выйти");

    private static final String fileMenu = String.join("", Collections.nCopies(50, "\n\r")) +
            String.join("\n", "Меню:",
                    "1 - Отсортировать по названию",
                    "2 - Отсортировать по федеральному округу",
                    "3 - Вывести город с максимальным населением",
                    "4 - Вывести численность городов в регионах",
                    "5 - Вывести города" +
                            "6 - Вывести это меню",
                    "7 - Загрузить другой файл",
                    "8 - Назад",
                    "9 - Выйти");

    private static final String dbMenu = String.join("", Collections.nCopies(50, "\n\r")) +
            String.join("\n", "Меню:",
                    "1 - Отсортировать по названию",
                    "2 - Отсортировать по федеральному округу",
                    "3 - Вывести город с максимальным населением",
                    "4 - Вывести численность городов в регионах",
                    "5 - Вывести города",
                    "6 - Вывести это меню",
                    "7 - Обновить данные",
                    "8 - Назад",
                    "9- Выйти");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CityDictionary cityDictionary = null;
            String command;
            State state = new State(null, 0);
            System.out.println(basicMenu);
            System.out.print("#");
            command = scanner.nextLine();
            boolean readyToRead = false;
            while (true) {
                if (readyToRead) {
                    System.out.print("#");
                    command = scanner.nextLine();
                }
                readyToRead = !readyToRead;
                switch (state.getValue()) {
                    case 0:
                        switch (command) {
                            case "1":
                                cityDictionary = new DbCityDictionary();
                                cityDictionary.update();
                                System.out.println(dbMenu);
                                state = state.getChild(2);
                                break;
                            case "2":
                                cityDictionary = readNewFile(scanner);
                                cityDictionary.update();
                                System.out.println(fileMenu);
                                state = state.getChild(1);
                                break;
                            case "3":
                                state = state.getChild(8);
                                readyToRead = false;
                                break;
                            case "4":
                                state = state.getChild(9);
                                readyToRead = false;
                                break;
                            default:
                                System.out.println("Команда отсутствует.");
                                readyToRead = true;
                        }
                        break;
                    case 1:
                        switch (command) {
                            case "1":
                                state = state.getChild(3);
                                break;
                            case "2":
                                state = state.getChild(4);
                                break;
                            case "3":
                                state = state.getChild(5);
                                break;
                            case "4":
                                state = state.getChild(6);
                                break;
                            case "5":
                                state = state.getChild(7);
                                break;
                            case "6":
                                state = state.getChild(8);
                                break;
                            case "7":
                                state = state.getChild(10);
                                break;
                            case "8":
                                state = state.getChild(13);
                                break;
                            case "9":
                                state = state.getChild(9);
                                break;
                            default:
                                state = state.getChild(12);
                                System.out.println("Команда отсутствует.");
                        }
                        readyToRead = false;
                        break;
                    case 2:
                        switch (command) {
                            case "1":
                                state = state.getChild(3);
                                break;
                            case "2":
                                state = state.getChild(4);
                                break;
                            case "3":
                                state = state.getChild(5);
                                break;
                            case "4":
                                state = state.getChild(6);
                                break;
                            case "5":
                                state = state.getChild(7);
                                break;
                            case "6":
                                state = state.getChild(8);
                                break;
                            case "7":
                                state = state.getChild(11);
                                break;
                            case "8":
                                state = state.getChild(13);
                                break;
                            case "9":
                                state = state.getChild(9);
                                break;
                            default:
                                state = state.getChild(12);
                                System.out.println("Команда отсутствует.");
                        }
                        readyToRead = false;
                        break;
                    case 3:
                        cityDictionary.sortByName();
                        state = state.getPrevious();
                        break;
                    case 4:
                        cityDictionary.sortByDistrict();
                        state = state.getPrevious();
                        break;
                    case 5:
                        Pair<Integer, Integer> pair = cityDictionary.findMaxPopulation();
                        System.out.printf("[%d] = %d\n", pair.getFirst(), pair.getSecond());
                        state = state.getPrevious();
                        break;
                    case 6:
                        cityDictionary.getCitiesByRegion().forEach((k, v) -> System.out.println(k + " : " + v));
                        state = state.getPrevious();
                        break;
                    case 7:
                        cityDictionary.getCities().forEach(System.out::println);
                        state = state.getPrevious();
                        break;
                    case 8:
                        switch (state.getPrevious().getValue()) {
                            case 1:
                                System.out.println(fileMenu);
                                break;
                            case 2:
                                System.out.println(dbMenu);
                                break;
                            case 0:
                                System.out.println(basicMenu);
                                break;
                        }
                        state = state.getPrevious();
                        break;
                    case 9:
                        return;
                    case 10:
                        cityDictionary = readNewFile(scanner);
                        cityDictionary.update();
                        break;
                    case 11:
                        cityDictionary.update();
                        state = state.getPrevious();
                        break;
                    case 12: //ошибка ввода
                        readyToRead = true;
                        state = state.getPrevious();
                        break;
                    case 13:
                        readyToRead = true;
                        state = state.getChild(0);
                        System.out.println(basicMenu);
                        break;
                }
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
            System.err.println("Произошла ошибка при чтении базы данных");
        } catch (IOException e) {
            System.err.println("Произошла ошибка при чтении пользовательского файла");
        }
    }

    private static FileCityDictionary readNewFile(Scanner scanner) {
        FileCityDictionary cityDictionary = null;
        String command;
        while (cityDictionary == null) {
            System.out.println("Введите путь к файлу:");
            command = scanner.nextLine();
            try {
                cityDictionary = new FileCityDictionary(command);
                cityDictionary.update();
                System.out.println("Данные загружены.");
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла");
                cityDictionary = null;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный путь. Указанный файл не существует или не доступен для чтения");
                cityDictionary = null;
            }
        }
        return cityDictionary;
    }
}
