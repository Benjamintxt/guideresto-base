package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.persistence.CityMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityService {

    private Connection connection;
    private TransactionService transactionService;

    public CityService(Connection connection) {
        this.connection = connection;
        this.transactionService = new TransactionService(connection);
    }
    public ArrayList<City> getAllCities(){
        CityMapper cityMapper = CityMapper.getInstance();
        return cityMapper.findAll();
    }

    public City insert(City city) {
        try {
            transactionService.startTransaction();
            CityMapper cityMapper = CityMapper.getInstance();
            cityMapper.insert(city);
            transactionService.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return null;
        }
        return city;
    }

    public City update(City city) {
        try {
            transactionService.startTransaction();
            CityMapper cityMapper = CityMapper.getInstance();
            cityMapper.update(city);
            transactionService.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return null;
        }
        return city;
    }

    public City delete(City city) {
        try {
            transactionService.startTransaction();
            CityMapper cityMapper = CityMapper.getInstance();
            cityMapper.delete(city);
            transactionService.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                transactionService.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return null;
        }
        return city;
    }

}
