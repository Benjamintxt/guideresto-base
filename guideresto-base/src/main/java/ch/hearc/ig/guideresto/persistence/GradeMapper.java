package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeMapper implements IMapper<Grade> {

    private static GradeMapper instance;

    public static GradeMapper getInstance() {
        if (instance == null) {
            instance = new GradeMapper();
        }
        return instance;
    }

    private Connection connection;

    public GradeMapper() {
        this.connection = DBOracle.createSession();
    }

    @Override
    public Grade insert(Grade grade) {
        try {
            String sql = "INSERT INTO NOTES (note, fk_comm, fk_crit) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, grade.getGrade());

                if (grade.getEvaluation() != null && grade.getEvaluation().getId() != null) {
                    int evaluationId = grade.getEvaluation().getId();
                    statement.setInt(2, evaluationId);
                } else {
                    throw new SQLException("CompleteEvaluation ID is null or incomplete.");
                }

                if (grade.getCriteria() != null && grade.getCriteria().getId() != null) {
                    statement.setInt(3, grade.getCriteria().getId());
                } else {
                    throw new SQLException("EvaluationCriteria ID is null or incomplete.");
                }

                statement.executeUpdate();
            }
            return grade;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Grade update(Grade grade) {
        try {
            String sql = "UPDATE NOTES SET note = ?, fk_comm = ?, fk_crit = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, grade.getGrade());
                statement.setInt(2, grade.getEvaluation().getId());
                statement.setInt(3, grade.getCriteria().getId());
                statement.setInt(4, grade.getId());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check == 0) {
                    throw new SQLException();
                } else {
                    return findByID(grade.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Grade grade) {
        try {
            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM NOTES WHERE numero = ?");
            deleteQuery.setInt(1, grade.getId());
            int check = deleteQuery.executeUpdate();
            if (check == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Grade findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM NOTES WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                return new Grade(
                        resultSet.getInt("numero"),
                        resultSet.getInt("note"),
                        CompleteEvaluationMapper.getInstance().findByID(resultSet.getInt("fk_comm")),
                        EvaluationCriteriaMapper.getInstance().findByID(resultSet.getInt("fk_crit"))
                );
            } else {
                System.out.println("Aucune note trouvée.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<Grade> findAll() {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            String sql = "SELECT numero, note, fk_comm, fk_crit FROM NOTES";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        int gradeValue = resultSet.getInt("note");
                        // Assuming you have methods to find CompleteEvaluation and EvaluationCriteria by ID
                        CompleteEvaluation evaluation = CompleteEvaluationMapper.getInstance().findByID(resultSet.getInt("fk_comm"));
                        EvaluationCriteria criteria = EvaluationCriteriaMapper.getInstance().findByID(resultSet.getInt("fk_crit"));
                        grades.add(new Grade(id, gradeValue, evaluation, criteria));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public ArrayList<Grade> findAllForEvaluation(int evaluationId) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            String sql = "SELECT numero, note, fk_comm, fk_crit FROM NOTES WHERE fk_comm = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, evaluationId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        int gradeValue = resultSet.getInt("note");
                        // Assuming you have methods to find CompleteEvaluation and EvaluationCriteria by ID
                        CompleteEvaluation evaluation = CompleteEvaluationMapper.getInstance().findByID(resultSet.getInt("fk_comm"));
                        EvaluationCriteria criteria = EvaluationCriteriaMapper.getInstance().findByID(resultSet.getInt("fk_crit"));
                        grades.add(new Grade(id, gradeValue, evaluation, criteria));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }


    public ArrayList<Grade> findAllForRestaurant(int restaurantId) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            String sql = "SELECT numero, note, fk_comm, fk_crit FROM NOTES WHERE fk_rest = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, restaurantId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        int gradeValue = resultSet.getInt("note");
                        // Assuming you have methods to find CompleteEvaluation and EvaluationCriteria by ID
                        CompleteEvaluation evaluation = CompleteEvaluationMapper.getInstance().findByID(resultSet.getInt("fk_comm"));
                        EvaluationCriteria criteria = EvaluationCriteriaMapper.getInstance().findByID(resultSet.getInt("fk_crit"));
                        grades.add(new Grade(id, gradeValue, evaluation, criteria));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

}
