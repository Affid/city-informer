package org.sber.bootcamp.cityinformer.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class ExeqSQL {
    public static void main(String[] args) throws IOException {
        try(Scanner in = args.length == 0 ? new Scanner(System.in) : new Scanner(Paths.get(args[0]).toFile())){
            try(Connection con = getConnection(); Statement stat = con.createStatement()){
                while (true){
                    if(args.length == 0){
                        System.out.println("Enter command or EXIT to exit:");
                        if(!in.hasNextLine()){
                            return;
                        }
                        String line = in.nextLine().trim();
                        if(line.equals("EXIT")){
                            return;
                        }
                        if(line.endsWith(";")){
                            line = line.substring(0, line.length() - 1);
                        }
                        try{
                            boolean isResult = stat.execute(line);
                            if(isResult){
                                try(ResultSet set = stat.getResultSet()){
                                    showResultSet(set);
                                }
                            }
                            else {
                                int updateCount = stat.getUpdateCount();
                                System.out.println(updateCount + " rows updated");
                            }
                        } catch (SQLException e){
                            for(Throwable t : e){
                                t.printStackTrace();
                            }
                        }
                    }
                }
            }catch (SQLException e){
                for(Throwable t: e){
                    t.printStackTrace();
                }
            }
        }
    }

    private static Connection getConnection() throws IOException, SQLException {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("src/main/resources/database.properties"))){
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if(drivers != null){
            System.setProperty("jdbc.drivers",drivers);
        }
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url,username,password);
    }

    private static void showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        for(int i = 1; i <= columnCount; i++){
            if(i > 1){
                System.out.print(", ");
            }
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();

        while (result.next()){
            for(int i = 1; i <= columnCount; i++){
                if(i > 1){
                    System.out.print(", ");
                }
                System.out.print(result.getString(i));
            }
            System.out.println();
        }
    }
}
