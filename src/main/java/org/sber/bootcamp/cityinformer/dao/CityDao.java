package org.sber.bootcamp.cityinformer.dao;

import org.sber.bootcamp.cityinformer.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class CityDao {
    private final Connection connection;

    private static CityDao instance;

    public static void main(String[] args) throws SQLException, IOException {
        CityDao dao = CityDao.CityDaoKeeper.getInstance();
        System.out.println(dao.groupCitiesByRegion());
    }

    private static final String all = "SELECT CITY.ID, CITY.NAME, REGION.NAME, DISTRICT.NAME, POPULATION, FOUNDATION " +
            "FROM CITY, REGION, DISTRICT " +
            "WHERE CITY.REGION = REGION.ID AND CITY.DISTRICT = DISTRICT.ID;";

    private static final String sortedByName = "SELECT CITY.ID, CITY.NAME, REGION.NAME, DISTRICT.NAME, POPULATION, FOUNDATION " +
            "FROM CITY, REGION, DISTRICT WHERE CITY.REGION = REGION.ID AND CITY.DISTRICT = DISTRICT.ID " +
            "ORDER BY CITY.NAME DESC;";

    private static final String sortedByDistrictAndName = "SELECT CITY.ID, CITY.NAME, REGION.NAME, DISTRICT.NAME, POPULATION, FOUNDATION " +
            "FROM CITY, REGION, DISTRICT " +
            "WHERE CITY.REGION = REGION.ID AND CITY.DISTRICT = DISTRICT.ID " +
            "ORDER BY DISTRICT.NAME, CITY.NAME;";

    private static final String groupCitiesByRegions = "SELECT A.NAME, COUNT(*) as NUMBER_OF_CITY " +
            "FROM REGION A " +
            "JOIN CITY C on A.ID = C.REGION " +
            "GROUP BY A.NAME;";


    private CityDao() throws SQLException, IOException {
        this.connection = getConnection();
    }

    public static class CityDaoKeeper{
        public static CityDao getInstance() throws SQLException, IOException {
            if(instance == null){
                instance = new CityDao();
            }
            return instance;
        }
    }

    private List<City> nonParametrizedQuery(String query){
        ArrayList<City> cities = new ArrayList<>();
        try(Statement stat = connection.createStatement()){
            ResultSet res = stat.executeQuery(query);
            while (res.next()){
                int id = res.getInt(1);
                String name = res.getString(2);
                String region = res.getString(3);
                String district = res.getString(4);
                int population = res.getInt(5);
                LocalDate foundation = res.getDate(6).toLocalDate();
                cities.add(new City(id,name,region, district, population, foundation));
            }
        } catch (SQLException e){
            for(Throwable t: e){
                t.printStackTrace();
            }
        }
        return cities;
    }

    public Map<String, Integer> groupCitiesByRegion(){
        HashMap<String, Integer> res = new HashMap<>();
        try(Statement stat = connection.createStatement()){
            ResultSet set = stat.executeQuery(groupCitiesByRegions);
            while (set.next()){
                String region = set.getString(1);
                int count = set.getInt(2);
                res.put(region,count);
            }
        } catch (SQLException e){
            for(Throwable t: e){
                t.printStackTrace();
            }
        }
        return res;
    }

    public List<City> all(){
        return nonParametrizedQuery(all);
    }

    public List<City> sortByName(){
        return nonParametrizedQuery(sortedByName);
    }

    public List<City> sortByDistrictAndName(){
        return nonParametrizedQuery(sortedByDistrictAndName);
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
}
