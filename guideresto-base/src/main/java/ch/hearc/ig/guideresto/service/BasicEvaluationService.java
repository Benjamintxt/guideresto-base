package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.persistence.BasicEvaluationMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicEvaluationService {

    private Connection connection;
    private TransactionService transactionService;
    public BasicEvaluationService(Connection connection) {
        this.connection = connection;
        this.transactionService = new TransactionService(connection);
    }
    public ArrayList<BasicEvaluation> getAllBasicEvaluations() {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        return basicEvaluationMapper.findAll();
    }

    public BasicEvaluation insert(BasicEvaluation basicEvaluation) {
        try {
            transactionService.startTransaction();
            BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
            basicEvaluationMapper.insert(basicEvaluation);
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
        return basicEvaluation;
    }

    public BasicEvaluation update(BasicEvaluation basicEvaluation) {
        try {
            transactionService.startTransaction();
            BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
            basicEvaluationMapper.update(basicEvaluation);
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
        return basicEvaluation;
    }

    public BasicEvaluation delete(BasicEvaluation basicEvaluation) {
        try {
            transactionService.startTransaction();
            BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
            basicEvaluationMapper.delete(basicEvaluation);
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
        return basicEvaluation;
    }

    public BasicEvaluation findByID(int id) {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        return basicEvaluationMapper.findByID(id);
    }
}
