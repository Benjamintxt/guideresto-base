package ch.hearc.ig.guideresto.service;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionService {

    private Connection connection;

    public TransactionService(Connection connection) {
        this.connection = connection;
    }

    public void startTransaction() throws SQLException {
        if (!connection.isClosed()) {
            connection.setAutoCommit(false);
        }
    }

    public void commitTransaction() throws SQLException {
        if (!connection.isClosed()) {
            System.out.println("Committing transaction...");
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Transaction committed.");
        }
    }

    public void rollbackTransaction() throws SQLException {
        if (!connection.isClosed()) {
            System.out.println("Rolling back transaction...");
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Transaction rolled back.");
        }
    }
}
