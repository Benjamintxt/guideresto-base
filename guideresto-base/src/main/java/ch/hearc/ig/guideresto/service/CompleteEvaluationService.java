package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.persistence.CompleteEvaluationMapper;

import java.util.ArrayList;

public class CompleteEvaluationService {

    public ArrayList<CompleteEvaluation> getAllCompleteEvaluations() {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        return completeEvaluationMapper.findAll();
    }

    public CompleteEvaluation insert(CompleteEvaluation completeEvaluation) {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        completeEvaluationMapper.insert(completeEvaluation);
        return completeEvaluation;
    }

    public CompleteEvaluation update(CompleteEvaluation completeEvaluation) {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        completeEvaluationMapper.update(completeEvaluation);
        return completeEvaluation;
    }

    public CompleteEvaluation delete(CompleteEvaluation completeEvaluation) {
        CompleteEvaluationMapper completeEvaluationMapper = CompleteEvaluationMapper.getInstance();
        completeEvaluationMapper.delete(completeEvaluation);
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
