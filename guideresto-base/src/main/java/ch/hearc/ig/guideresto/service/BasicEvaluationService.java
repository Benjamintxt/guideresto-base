package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.persistence.BasicEvaluationMapper;
import ch.hearc.ig.guideresto.persistence.RestaurantMapper;

import java.util.ArrayList;

public class BasicEvaluationService {

    public ArrayList<BasicEvaluation> getAllBasicEvaluations() {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        return basicEvaluationMapper.findAll();
    }

    public BasicEvaluation insert(BasicEvaluation basicEvaluation) {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        basicEvaluationMapper.insert(basicEvaluation);
        return basicEvaluation;
    }

    public BasicEvaluation update(BasicEvaluation basicEvaluation) {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        basicEvaluationMapper.update(basicEvaluation);
        return basicEvaluation;
    }

    public BasicEvaluation delete(BasicEvaluation basicEvaluation) {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        basicEvaluationMapper.delete(basicEvaluation);
        return basicEvaluation;
    }

    public BasicEvaluation findByID(int id) {
        BasicEvaluationMapper basicEvaluationMapper = BasicEvaluationMapper.getInstance();
        return basicEvaluationMapper.findByID(id);
    }
}
