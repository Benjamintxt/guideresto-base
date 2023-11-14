package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvaluationCriteriaMapper implements IMapper<EvaluationCriteria> {

    private static EvaluationCriteriaMapper instance;

    public static EvaluationCriteriaMapper getInstance() {
        if (instance == null) {
            instance = new EvaluationCriteriaMapper();
        }
        return instance;
    }

    private Connection connection;

    public EvaluationCriteriaMapper() {
        this.connection = DBOracle.createSession();
    }

    @Override
    public EvaluationCriteria insert(EvaluationCriteria criteria) {
        try {
            String sql = "INSERT INTO CRITERES_EVALUATION (nom, description) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, criteria.getName());
                statement.setString(2, criteria.getDescription());
                statement.executeUpdate();

            }
            return criteria;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EvaluationCriteria update(EvaluationCriteria criteria) {
        try {
            String sql = "UPDATE CRITERES_EVALUATION SET nom = ?, description = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, criteria.getName());
                statement.setString(2, criteria.getDescription());
                statement.setInt(3, criteria.getId());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check == 0) {
                    throw new SQLException();
                } else {
                    return findByID(criteria.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(EvaluationCriteria criteria) {
        try {
            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM CRITERES_EVALUATION WHERE numero = ?");
            deleteQuery.setInt(1, criteria.getId());
            int check = deleteQuery.executeUpdate();
            if (check == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EvaluationCriteria findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM CRITERES_EVALUATION WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                return new EvaluationCriteria(
                        resultSet.getInt("numero"),
                        resultSet.getString("nom"),
                        resultSet.getString("description")
                );
            } else {
                System.out.println("Aucun critère d'évaluation trouvé.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<EvaluationCriteria> findAll() {
        ArrayList<EvaluationCriteria> criteriaList = new ArrayList<>();
        try {
            String sql = "SELECT numero, nom, description FROM CRITERES_EVALUATION";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        String name = resultSet.getString("nom");
                        String description = resultSet.getString("description");
                        criteriaList.add(new EvaluationCriteria(id, name, description));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return criteriaList;
    }
}
