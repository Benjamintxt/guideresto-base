package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantTypeMapper {

    private static RestaurantTypeMapper instance;

    public static RestaurantTypeMapper getInstance() {
        if (instance == null) {
            instance = new RestaurantTypeMapper();
        }
        return instance;
    }

    private Connection connection;

    public RestaurantTypeMapper() {
        this.connection = DBOracle.createSession();
    }

    public RestaurantType findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM TYPES_GASTRONOMIQUES WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                return new RestaurantType(
                        resultSet.getInt("numero"),
                        resultSet.getString("libelle"),
                        resultSet.getString("description")
                );
            } else {
                System.out.println("Aucun type de restaurant trouvé.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<RestaurantType> findAll() {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM TYPES_GASTRONOMIQUES");
            ResultSet resultSet = query.executeQuery();
            ArrayList<RestaurantType> restaurantTypes = new ArrayList<>();

            while (resultSet.next()) {
                restaurantTypes.add(new RestaurantType(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("LIBELLE"),
                        resultSet.getString("DESCRIPTION")
                ));
            }
            return restaurantTypes;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

}
