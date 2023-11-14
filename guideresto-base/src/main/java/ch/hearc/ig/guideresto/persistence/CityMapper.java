package ch.hearc.ig.guideresto.persistence;
import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityMapper implements IMapper<City>{

    private static CityMapper instance;

    public static CityMapper getInstance() {
        if (instance == null) {
            instance = new CityMapper();
        }
        return instance;
    }
    private Connection connection;
    public CityMapper(){
        this.connection = DBOracle.createSession();
    }
    @Override
    public City insert(City city) {
        try {
            String sql = "INSERT INTO VILLES (code_postal, nom_ville) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, city.getZipCode());
                statement.setString(2, city.getCityName());
                statement.executeUpdate();
            }
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

@Override
    public City update(City city) {
        try {
            String sql = "UPDATE VILLES SET code_postal = ?, nom_ville = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, city.getZipCode());
                statement.setString(2, city.getCityName());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check==0){
                    throw new SQLException();
                } else {
                    return findByID(city.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(City city) {
        try{
            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM villes WHERE numero = ?");
            deleteQuery.setInt(1, city.getId());
            int check = deleteQuery.executeUpdate();
            if (check==0){
                throw new SQLException();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public City findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM villes WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()){
                return new City(
                        resultSet.getInt("numero"),
                        resultSet.getString("CODE_POSTAL"),
                        resultSet.getString("nom_ville")
                );
            }
            else {
                System.out.println("Aucune ville trouvée.");
                return null;
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return null;


    }

    @Override
    public ArrayList<City> findAll() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            String sql = "SELECT numero, code_postal, nom_ville FROM VILLES";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        String zipCode = resultSet.getString("code_postal");
                        String cityName = resultSet.getString("nom_ville");
                        cities.add(new City(id, zipCode, cityName));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}