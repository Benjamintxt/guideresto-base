package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CompleteEvaluationMapper implements IMapper<CompleteEvaluation> {

    private static CompleteEvaluationMapper instance;

    public static CompleteEvaluationMapper getInstance() {
        if (instance == null) {
            instance = new CompleteEvaluationMapper();
        }
        return instance;
    }

    private Connection connection;

    public CompleteEvaluationMapper() {
        this.connection = DBOracle.createSession();
    }

    @Override
    public CompleteEvaluation insert(CompleteEvaluation completeEvaluation) {
        Restaurant restaurant = RestaurantMapper.getInstance().findByID(completeEvaluation.getRestaurant().getId());

        try {
            String sql = "INSERT INTO COMMENTAIRES (date_eval, commentaire, nom_utilisateur, fk_rest) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, new String[]{"numero"})) {
                statement.setObject(1, Date.valueOf(completeEvaluation.getVisitDate()));
                statement.setString(2, completeEvaluation.getComment());
                statement.setString(3, completeEvaluation.getUsername());
                statement.setInt(4, restaurant.getId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Insertion failed, no rows affected.");
                }

                // Get the last inserted ID
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        // Set the generated ID in the CompleteEvaluation object
                        completeEvaluation.setId(generatedId);

                        // Insert grades
                        for (Grade grade : completeEvaluation.getGrades()) {
                            // Set the CompleteEvaluation object in each Grade
                            grade.setEvaluation(completeEvaluation);
                            GradeMapper.getInstance().insert(grade);
                        }
                    } else {
                        throw new SQLException("Insertion failed, no ID obtained.");
                    }
                }
            }
            return completeEvaluation;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public CompleteEvaluation update(CompleteEvaluation completeEvaluation) {
        try {
            String sql = "UPDATE COMMENTAIRES SET date_eval = ?, commentaire = ?, nom_utilisateur = ?, fk_rest = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, Date.valueOf(completeEvaluation.getVisitDate()));
                statement.setString(2, completeEvaluation.getComment());
                statement.setString(3, completeEvaluation.getUsername());
                statement.setInt(4, completeEvaluation.getRestaurant().getId());  // Assuming getId() exists in Restaurant
                statement.setInt(5, completeEvaluation.getId());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check == 0) {
                    throw new SQLException();
                } else {
                    return findByID(completeEvaluation.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(CompleteEvaluation completeEvaluation) {
        try {
            for (Grade grade : completeEvaluation.getGrades()) {
                GradeMapper.getInstance().delete(grade);
            }

            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM COMMENTAIRES WHERE numero = ?");
            deleteQuery.setInt(1, completeEvaluation.getId());
            int check = deleteQuery.executeUpdate();
            if (check == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompleteEvaluation findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM COMMENTAIRES WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                return new CompleteEvaluation(
                        resultSet.getInt("numero"),
                        resultSet.getDate("date_eval").toLocalDate(),
                        // Assuming you have methods to find Restaurant by ID
                        RestaurantMapper.getInstance().findByID(resultSet.getInt("fk_rest")),
                        resultSet.getString("commentaire"),
                        resultSet.getString("nom_utilisateur")
                );
            } else {
                System.out.println("Aucune évaluation complète trouvée.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<CompleteEvaluation> findAll() {
        ArrayList<CompleteEvaluation> evaluations = new ArrayList<>();
        try {
            String sql = "SELECT numero, date_eval, commentaire, nom_utilisateur, fk_rest FROM COMMENTAIRES";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("numero");
                        LocalDate dateEval = resultSet.getDate("date_eval").toLocalDate();
                        // Assuming you have methods to find Restaurant by ID
                        Restaurant restaurant = RestaurantMapper.getInstance().findByID(resultSet.getInt("fk_rest"));
                        String comment = resultSet.getString("commentaire");
                        String username = resultSet.getString("nom_utilisateur");

                        // Fetch associated grades
                        ArrayList<Grade> grades = GradeMapper.getInstance().findAllForEvaluation(id);

                        CompleteEvaluation completeEvaluation = new CompleteEvaluation(id, dateEval, restaurant, comment, username);
                        completeEvaluation.getGrades().addAll(grades);
                        evaluations.add(completeEvaluation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }
    public ArrayList<CompleteEvaluation> findByRestaurant(Restaurant restaurant) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM COMMENTAIRES WHERE fk_rest = ?");
            query.setInt(1, restaurant.getId());
            ResultSet resultSet = query.executeQuery();
            ArrayList<CompleteEvaluation> evaluations = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("numero");
                LocalDate dateEval = resultSet.getDate("date_eval").toLocalDate();
                String comment = resultSet.getString("commentaire");
                String username = resultSet.getString("nom_utilisateur");

                // Fetch associated grades
                ArrayList<Grade> grades = GradeMapper.getInstance().findAllForEvaluation(id);

                CompleteEvaluation completeEvaluation = new CompleteEvaluation(id, dateEval, restaurant, comment, username);
                completeEvaluation.getGrades().addAll(grades);
                evaluations.add(completeEvaluation);
            }

            return evaluations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deleteByRestaurant(Restaurant restaurant) throws SQLException{
        // Fetch and delete all complete evaluations for the given restaurant
        ArrayList<CompleteEvaluation> evaluations = findByRestaurant(restaurant);
        for (CompleteEvaluation evaluation : evaluations) {
            delete(evaluation);
        }
    }
}
