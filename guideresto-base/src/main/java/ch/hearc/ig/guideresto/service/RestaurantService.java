package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.CityMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantTypeMapper;

import java.util.ArrayList;

public class RestaurantService {

    public ArrayList<Restaurant> getAllRestaurants() {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findAll();
    }

    public Restaurant getRestaurantById(int id) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        return restaurantMapper.findByID(id);
    }

    public Restaurant insert(Restaurant restaurant) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        restaurantMapper.insert(restaurant);
        return restaurant;
    }

    public Restaurant update(Restaurant restaurant) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        restaurantMapper.update(restaurant);
        return restaurant;
    }

    public void delete(Restaurant restaurant) {
        RestaurantMapper restaurantMapper = RestaurantMapper.getInstance();
        restaurantMapper.delete(restaurant);
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
