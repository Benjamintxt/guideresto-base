package ch.hearc.ig.guideresto.business;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class City {
    private Integer id;
    private String zipCode;
    private String cityName;
    private ArrayList<Restaurant> restaurants;

    public City(Integer id, String zipCode, String cityName) {
        this.id = id;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.restaurants = new ArrayList<Restaurant>() {
        };
    }

    public Integer getId() {
        return id;
    }
    public String getZipCode() {
        return zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    @Override
    public String toString() {
        return "City [zipCode=" + this.zipCode + ", cityName=" + this.cityName + "]";
    }
}