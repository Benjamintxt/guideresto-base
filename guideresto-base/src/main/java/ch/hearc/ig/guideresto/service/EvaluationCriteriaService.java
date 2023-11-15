package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.persistence.CityMapper;
import ch.hearc.ig.guideresto.persistence.EvaluationCriteriaMapper;

import java.util.ArrayList;

public class EvaluationCriteriaService {
    //Transactions not implemented here because we don't handle insert/update/method on EvaluationCriteria yet
    //maybe for future use
    public ArrayList<EvaluationCriteria> getAllEvaluationCriteria() {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        return criteriaMapper.findAll();
    }

    public EvaluationCriteria insert(EvaluationCriteria criteria) {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        criteriaMapper.insert(criteria);
        return criteria;
    }

    public EvaluationCriteria update(EvaluationCriteria criteria) {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        criteriaMapper.update(criteria);
        return criteria;
    }

    public EvaluationCriteria delete(EvaluationCriteria criteria) {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        criteriaMapper.delete(criteria);
        return criteria;
    }

    public EvaluationCriteria findByID(int id) {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        return criteriaMapper.findByID(id);
    }

    public ArrayList<EvaluationCriteria> findAll() {
        EvaluationCriteriaMapper criteriaMapper = EvaluationCriteriaMapper.getInstance();
        return criteriaMapper.findAll();
    }

}
