package org.sber.bootcamp.cityinformer;

import org.sber.bootcamp.cityinformer.entities.City;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<City> cities = null;
        boolean stop = false;
        System.out.println("Меню:\n\t1 - ввести данные с консоли\n\t2 - ввести данные из файла\n\t3 - выход");
        while (!stop) {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    cities = consoleRead(scanner);
                    break;
                case 2:
                    System.out.println("Введите путь к файлу:");
                    scanner.nextLine();
                    String path = scanner.nextLine();
                    Path filePath = Paths.get(path);
                    boolean inputEnded = filePath.toFile().isFile();
                    while (!inputEnded) {
                        System.out.println("Файл не найден. Повторите ввод? (да/нет)");
                        path = scanner.nextLine();
                        if ("да".equals(path)) {
                            path = scanner.nextLine();
                            filePath = Paths.get(path);
                            inputEnded = filePath.toFile().isFile();
                        } else {
                            stop = true;
                            inputEnded = true;
                        }
                    }
                    if (!stop) {
                        try {
                            cities = fileRead(filePath);
                        }
                        catch (IOException e){
                            Logger.getLogger("Main").log(Level.WARNING, "ОШИБКА ЧТЕНИЯ ФАЙЛА "+ filePath,e);
                        }
                    }
                    break;
                case 3:
                    stop = true;
                    break;
                default:
                    System.out.println("Некорректная опция. Повторите ввод.");
                    break;
            }
            if(cities != null){
                System.out.println(cities);
            }
            if (!stop) {
                System.out.println("Хотите повторить?(да/нет)");
                String answer = scanner.nextLine();
                if ("да".equals(answer)) {
                    System.out.println("Меню:\n\t1 - ввести данные с консоли\n\t2 - ввести данные из файла\n\t3 - выход");
                }
                else
                    stop = true;
            }
        }
        scanner.close();
    }

    private static List<City> consoleRead(Scanner scanner) {
        System.out.print("Число записей: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        ArrayList<City> cities = new ArrayList<>();
        while (count-- > 0) {
            cities.add(readCity(scanner));
        }
        return cities;
    }

    private static List<City> fileRead(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        ArrayList<City> cities = new ArrayList<>();
        for(String line: lines){
            cities.add(readCity(new Scanner(line)));
        }
        return cities;
    }


    private static City readCity(Scanner scanner) {
        String rawData = scanner.nextLine();
        String[] data = rawData.split(";");
        String name = data[1];
        String region = data[2];
        String district = data[3];
        int population = Integer.parseInt(data[4]);
        LocalDate date = LocalDate.of(Integer.parseInt(data[5]), 1, 1);
        return new City(name, region, district, population, date);
    }
}
