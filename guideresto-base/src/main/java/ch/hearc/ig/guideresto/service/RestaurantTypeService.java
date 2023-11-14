package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.RestaurantTypeMapper;

import java.util.ArrayList;

public class RestaurantTypeService {

    public ArrayList<RestaurantType> getAllRestaurantTypes() {
        RestaurantTypeMapper restaurantTypeMapper = RestaurantTypeMapper.getInstance();
        return restaurantTypeMapper.findAll();
    }

    public RestaurantType getRestaurantTypeById(int id) {
        RestaurantTypeMapper restaurantTypeMapper = RestaurantTypeMapper.getInstance();
        return restaurantTypeMapper.findByID(id);
    }

}
