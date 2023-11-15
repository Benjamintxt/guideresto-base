package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.persistence.CompleteEvaluationMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompleteEvaluationService {

    private Connection connection;
    private TransactionService transactionService;

    public CompleteEvaluationService(Connection connection) {
        this.connection = connection;
        this.transactionService = new TransactionService(connection);
    }
    public ArrayList<CompleteEvaluation> getAllCompleteEvaluations() {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        return completeEvaluationMapper.findAll();
    }

    public CompleteEvaluation insert(CompleteEvaluation completeEvaluation) {
        try {
            transactionService.startTransaction();
            CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
            completeEvaluationMapper.insert(completeEvaluation);
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
        return completeEvaluation;
    }

    public CompleteEvaluation update(CompleteEvaluation completeEvaluation) {
        try {
            transactionService.startTransaction();
            CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
            completeEvaluationMapper.update(completeEvaluation);
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
        return completeEvaluation;
    }

    public CompleteEvaluation delete(CompleteEvaluation completeEvaluation) {
        try {
            transactionService.startTransaction();
            CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
            completeEvaluationMapper.delete(completeEvaluation);
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
        return completeEvaluation;
    }

    public CompleteEvaluation findByID(int id) {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        return completeEvaluationMapper.findByID(id);
    }

    public ArrayList<CompleteEvaluation> findByRestaurant(Restaurant restaurant) {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        return completeEvaluationMapper.findByRestaurant(restaurant);
    }

}
