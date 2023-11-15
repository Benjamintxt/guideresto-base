package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.CityMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantTypeMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantService {

    private Connection connection;
    private TransactionService transactionService;

    public RestaurantService(Connection connection) {
        this.connection = connection;
        this.transactionService = new TransactionService(connection);
    }

    public ArrayList<Restaurant> getAllRestaurants() {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findAll();
    }

    public Restaurant getRestaurantById(int id) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findByID(id);
    }

    public Restaurant insert(Restaurant restaurant) {
        try {
            transactionService.startTransaction();

            RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
            restaurantMapper.insert(restaurant);

            transactionService.commitTransaction();
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return null;
        }
    }

    public Restaurant update(Restaurant restaurant) {
        try {
            transactionService.startTransaction();

            RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
            restaurantMapper.update(restaurant);

            transactionService.commitTransaction();
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return null;
        }
    }

    public void delete(Restaurant restaurant) {
        try {
            transactionService.startTransaction();

            RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
            restaurantMapper.delete(restaurant);

            transactionService.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    public ArrayList<Restaurant> getAllRestaurantsForCity(City city) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findAllForCity(city);
    }

    public ArrayList<Restaurant> getAllRestaurantsForType(RestaurantType restaurantType) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findAllForType(restaurantType);
    }
}
