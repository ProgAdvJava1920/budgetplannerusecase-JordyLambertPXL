package be.pxl.student.JDBC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager {
    Connection connection;
    Logger logger = LogManager.getLogger(AccountDAO.class);
    private String url;
    private String username;
    private String password;

    public DAOManager(String url) {
        this.url = url;
    }

    public DAOManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //Maken van connection
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    //Sluiten van connection
    public void close() {

        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            logger.warn("Error closing connection", e);
        }
    }

    //Commit en Rollback
    public void commit() throws SQLException {
        if (connection != null) connection.commit();
    }

    public void rollback(Exception originalException) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.warn("Rollback failed", originalException);
            }
        }
    }
}
