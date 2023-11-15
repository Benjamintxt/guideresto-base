package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.persistence.CompleteEvaluationMapper;
import ch.hearc.ig.guideresto.persistence.EvaluationCriteriaMapper;
import ch.hearc.ig.guideresto.persistence.GradeMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeService {
    private Connection connection;
    private TransactionService transactionService;

    public GradeService(Connection connection) {
        this.connection = connection;
        this.transactionService = new TransactionService(connection);
    }
    public ArrayList<Grade> getAllGrades() {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        return gradeMapper.findAll();
    }

    public Grade insert(Grade grade) {
        try {
            transactionService.startTransaction();
            GradeMapper gradeMapper = GradeMapper.getInstance();
            gradeMapper.insert(grade);
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
        return grade;
    }

    public Grade update(Grade grade) {
        try {
            transactionService.startTransaction();
            GradeMapper gradeMapper = GradeMapper.getInstance();
            gradeMapper.update(grade);
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
        return grade;
    }

    public Grade delete(Grade grade) {
        try {
            transactionService.startTransaction();
            GradeMapper gradeMapper = GradeMapper.getInstance();
            gradeMapper.delete(grade);
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
        return grade;
    }

    public ArrayList<Grade> findAllForEvaluation(int evaluationId) {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        return gradeMapper.findAllForEvaluation(evaluationId);
    }

    public ArrayList<Grade> findAllForRestaurant(int restaurantId) {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        return gradeMapper.findAllForRestaurant(restaurantId);
    }
}
