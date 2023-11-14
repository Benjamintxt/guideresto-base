package ch.hearc.ig.guideresto.business;

import java.util.*;

public class RestaurantType {

    private Integer id;
    private String label;
    private String description;
    private List<Restaurant> restaurants;

    public RestaurantType(Integer id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.restaurants = new ArrayList<>() {

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Restaurant> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Restaurant restaurant) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Restaurant> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Restaurant> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Restaurant get(int index) {
                return null;
            }

            @Override
            public Restaurant set(int index, Restaurant element) {
                return null;
            }

            @Override
            public void add(int index, Restaurant element) {

            }

            @Override
            public Restaurant remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Restaurant> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Restaurant> listIterator(int index) {
                return null;
            }

            @Override
            public List<Restaurant> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }
    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}