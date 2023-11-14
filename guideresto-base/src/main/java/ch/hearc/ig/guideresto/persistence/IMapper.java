package ch.hearc.ig.guideresto.persistence;
import java.util.ArrayList;

public interface IMapper<T> {
    T findByID(int pk);
    ArrayList<T> findAll();
    T insert(T t);
    T update(T t);
    void delete(T t);
}