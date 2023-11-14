package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BasicEvaluationMapper implements IMapper<BasicEvaluation> {

    private static BasicEvaluationMapper instance;
    private Connection connection;

    public static BasicEvaluationMapper getInstance() {
        if (instance == null) {
            instance = new BasicEvaluationMapper();
        }
        return instance;
    }

    private BasicEvaluationMapper() {
        this.connection = DBOracle.createSession();
    }

    @Override
    public BasicEvaluation insert(BasicEvaluation basicEvaluation) {
        try {
            String sql = "INSERT INTO LIKES (appreciation, date_eval, adresse_ip, fk_rest) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, basicEvaluation.isLikeRestaurant() ? "T" : "F");
                statement.setDate(2, java.sql.Date.valueOf(basicEvaluation.getVisitDate()));
                statement.setString(3, basicEvaluation.getIpAddress());
                int restaurantId = basicEvaluation.getRestaurant().getId();
                statement.setInt(4, restaurantId);
                statement.executeUpdate();
            }

            return basicEvaluation;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BasicEvaluation update(BasicEvaluation basicEvaluation) {
        try {
            String sql = "UPDATE LIKES SET appreciation = ?, date_eval = ?, adresse_ip = ?, fk_rest = ? WHERE numero = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, basicEvaluation.isLikeRestaurant() ? "Y" : "N");
                statement.setDate(2, java.sql.Date.valueOf(basicEvaluation.getVisitDate()));
                statement.setString(3, basicEvaluation.getIpAddress());
                statement.setInt(4, basicEvaluation.getRestaurant().getId());
                statement.setInt(5, basicEvaluation.getId());
                statement.executeUpdate();

                int check = statement.executeUpdate();
                if (check == 0) {
                    throw new SQLException();
                } else {
                    return findByID(basicEvaluation.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(BasicEvaluation basicEvaluation) {
        try {
            PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM LIKES WHERE numero = ?");
            deleteQuery.setInt(1, basicEvaluation.getId());
            int check = deleteQuery.executeUpdate();
            if (check == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BasicEvaluation findByID(int pk) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM LIKES WHERE numero = ?");
            query.setInt(1, pk);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                Restaurant restaurant = getRestaurantFromResultSet(resultSet);
                return new BasicEvaluation(
                        resultSet.getInt("numero"),
                        resultSet.getDate("date_eval").toLocalDate(),
                        restaurant,
                        resultSet.getString("appreciation").equals("Y"),
                        resultSet.getString("adresse_ip")
                );
            } else {
                System.out.println("Aucune évaluation trouvée.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<BasicEvaluation> findAll() {
        ArrayList<BasicEvaluation> basicEvaluations = new ArrayList<>();
        try {
            String sql = "SELECT numero, appreciation, date_eval, adresse_ip, fk_rest FROM LIKES";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Assuming you have a method to retrieve the associated Restaurant
                        // Replace getRestaurantFromResultSet with your actual method
                        Restaurant restaurant = getRestaurantFromResultSet(resultSet);
                        BasicEvaluation basicEvaluation = new BasicEvaluation(
                                resultSet.getInt("numero"),
                                resultSet.getDate("date_eval").toLocalDate(),
                                restaurant,
                                resultSet.getString("appreciation").equals("Y"),
                                resultSet.getString("adresse_ip")
                        );
                        basicEvaluations.add(basicEvaluation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basicEvaluations;
    }

    public ArrayList<BasicEvaluation> findByRestaurant(Restaurant restaurant) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM LIKES WHERE fk_rest = ?");
            query.setInt(1, restaurant.getId());
            ResultSet resultSet = query.executeQuery();
            ArrayList<BasicEvaluation> evaluations = new ArrayList<>();

            while (resultSet.next()) {
                BasicEvaluation basicEvaluation = new BasicEvaluation(
                        resultSet.getInt("numero"),
                        resultSet.getDate("date_eval").toLocalDate(),
                        restaurant,
                        resultSet.getString("appreciation").equals("Y"),
                        resultSet.getString("adresse_ip")
                );
                evaluations.add(basicEvaluation);
            }

            return evaluations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteByRestaurant(Restaurant restaurant)throws SQLException{
        // Fetch and delete all basic evaluations for the given restaurant
        ArrayList<BasicEvaluation> evaluations = findByRestaurant(restaurant);
        for (BasicEvaluation evaluation : evaluations) {
            delete(evaluation);
        }
    }




    private Restaurant getRestaurantFromResultSet(ResultSet resultSet) throws SQLException {
        int restaurantId = resultSet.getInt("fk_rest");
        return RestaurantMapper.getInstance().findByID(restaurantId);
    }
}
