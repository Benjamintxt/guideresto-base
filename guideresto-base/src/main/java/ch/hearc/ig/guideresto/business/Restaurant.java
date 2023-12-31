package ch.hearc.ig.guideresto.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Restaurant {

    private Integer id;
    private String name;
    private String description;
    private String website;
    private ArrayList<Evaluation> evaluations;
    private Localisation address;
    private RestaurantType type;

    public Restaurant(Integer id, String name, String description, String website, String street, City city, RestaurantType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.evaluations = new ArrayList<>();
        this.address = new Localisation(street, city);
        this.type = type;
    }
    public void setEvaluations(ArrayList<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return address.getCity().getZipCode();
    }

    public String getCityName() {
        return address.getCity().getCityName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }

    public Localisation getAddress() {
        return address;
    }

    public RestaurantType getType() {
        return type;
    }

    public void setType(RestaurantType type) {
        this.type = type;
    }

    public void setId(int id) {
    }
}