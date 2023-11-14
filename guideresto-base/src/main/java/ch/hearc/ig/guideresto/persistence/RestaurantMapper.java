package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantMapper implements IMapper<Restaurant> {

    private static RestaurantMapper instance;

    public static RestaurantMapper getInstance() {
        if (instance == null) {
            instance = new RestaurantMapper();
        }
        return instance;
    }

    private Connection connection;

    public RestaurantMapper() {
        this.connection = DBOracle.createSession();
    }

    @Override
    public Restaurant insert(Restaurant restaurant) {
        try {
            String sql = "INSERT INTO RESTAURANTS (nom, description, site_web, adresse, fk_type, fk_vill) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, new String[]{"numero"})) {
                statement.setString(1, restaurant.getName());
                statement.setString(2, restaurant.getDescription());
                statement.setString(3, restaurant.getWebsite());
                statement.setString(4, restaurant.getAddress().getStreet());
                statement.setInt(5, restaurant.getType().getId());
                statement.setInt(6, restaurant.getAddress().getCity().getId());

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    restaurant.setId(generatedKeys.getInt(1));
                }
            }
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Restaurant update(Restaurant restaurant) {
        try {
            String sql = "UPDATE RESTAURANTS SET nom = ?, adresse = ?, description = ?, site_web = ?, " +
                    "fk_type = ?, fk_vill = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, restaurant.getName());
                statement.setString(2, restaurant.getAddress().getStreet());
                statement.setString(3, restaurant.getDescription());
                statement.setString(4, restaurant.getWebsite());

                RestaurantTypeMapper typeMapper = RestaurantTypeMapper.getInstance();
                RestaurantType restaurantType = typeMapper.findByID(restaurant.getType().getId());

                statement.setInt(5, restaurantType.getId());

                CityMapper cityMapper = CityMapper.getInstance();
                City city = cityMapper.findByID(restaurant.getAddress().getCity().getId());

                statement.setInt(6, city.getId());
                statement.setInt(7, restaurant.getId());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check == 0) {
                    throw new SQLException();
                } else {
                    return findByID(restaurant.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Restaurant restaurant) {
        try {
            CompleteEvaluationMapper.getInstance().deleteByRestaurant(restaurant);
            BasicEvaluationMapper.getInstance().deleteByRestaurant(restaurant);

            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM RESTAURANTS WHERE numero = ?");
            deleteQuery.setInt(1, restaurant.getId());
            int check = deleteQuery.executeUpdate();
            if (check == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM RESTAURANTS WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                int restaurantId = resultSet.getInt("numero");
                String name = resultSet.getString("nom");
                String address = resultSet.getString("adresse");
                String description = resultSet.getString("description");
                String website = resultSet.getString("site_web");
                int typeId = resultSet.getInt("fk_type");
                int cityId = resultSet.getInt("fk_vill");

                // Fetch the related RestaurantType and City objects
                RestaurantType restaurantType = RestaurantTypeMapper.getInstance().findByID(typeId);
                City city = CityMapper.getInstance().findByID(cityId);

                // Create and return the Restaurant object
                return new Restaurant(restaurantId, name, address, description, website, city, restaurantType);
            } else {
                System.out.println("Aucun restaurant trouvé.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ArrayList<Restaurant> findAll() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            String sql = "SELECT numero, nom, adresse, description, site_web, fk_type, fk_vill FROM RESTAURANTS";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        String name = resultSet.getString("nom");
                        String address = resultSet.getString("adresse");
                        String description = resultSet.getString("description");
                        String website = resultSet.getString("site_web");
                        int typeId = resultSet.getInt("fk_type");
                        RestaurantType restaurantType = RestaurantTypeMapper.getInstance().findByID(typeId);
                        int cityId = resultSet.getInt("fk_vill");
                        City city = CityMapper.getInstance().findByID(cityId);
                        restaurants.add(new Restaurant(id, name, description, website, address, city, restaurantType));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    // ... (your existing methods)

    public ArrayList<Restaurant> findAllForCity(City city) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            String sql = "SELECT numero, nom, adresse, description, site_web, fk_type, fk_vill FROM RESTAURANTS WHERE fk_vill = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, city.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        String name = resultSet.getString("nom");
                        String address = resultSet.getString("adresse");
                        String description = resultSet.getString("description");
                        String website = resultSet.getString("site_web");
                        int typeId = resultSet.getInt("fk_type");
                        RestaurantType restaurantType = RestaurantTypeMapper.getInstance().findByID(typeId);
                        restaurants.add(new Restaurant(id, name, description, website, address, city, restaurantType));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public ArrayList<Restaurant> findAllForType(RestaurantType restaurantType) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            String sql = "SELECT numero, nom, adresse, description, site_web, fk_type, fk_vill FROM RESTAURANTS WHERE fk_type = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, restaurantType.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        String name = resultSet.getString("nom");
                        String address = resultSet.getString("adresse");
                        String description = resultSet.getString("description");
                        String website = resultSet.getString("site_web");
                        int cityId = resultSet.getInt("fk_vill");
                        City city = CityMapper.getInstance().findByID(cityId);
                        restaurants.add(new Restaurant(id, name, description, website, address, city, restaurantType));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

}
