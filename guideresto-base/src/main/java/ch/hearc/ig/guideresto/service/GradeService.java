package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.persistence.CompleteEvaluationMapper;
import ch.hearc.ig.guideresto.persistence.EvaluationCriteriaMapper;
import ch.hearc.ig.guideresto.persistence.GradeMapper;

import java.util.ArrayList;

public class GradeService {

    public ArrayList<Grade> getAllGrades() {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        return gradeMapper.findAll();
    }

    public Grade insert(Grade grade) {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        gradeMapper.insert(grade);
        return grade;
    }

    public Grade update(Grade grade) {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        gradeMapper.update(grade);
        return grade;
    }

    public Grade delete(Grade grade) {
        GradeMapper gradeMapper = GradeMapper.getInstance();
        gradeMapper.delete(grade);
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
