package be.pxl.student.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOManager {
    Connection connection;
    Logger logger = LogManager.getLogger(AccountDAO.class);
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
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
    public void getConnection() throws SQLException {
        //region Used for JDBC
        /*
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        }
        return connection;

         */
        //endregion

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(url);
            entityManager = entityManagerFactory.createEntityManager();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }

    //Sluiten van connection
    public void close() {
        //region Used for JDBC
        /*
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            logger.warn("Error closing connection", e);
        }
         */
        //endregion
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    //Commit en Rollback
    public void commit() throws SQLException {
        //region Used for JDBC
        /*if (connection != null) connection.commit();*/
        //endregion
        if (entityManager != null) entityManager.getTransaction().commit();
    }

    public void rollback(Exception originalException) {
        //region Used for JDBC
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.warn("Rollback failed", originalException);
            }
        }
        //endregion
        if (entityManager != null) {
            entityManager.getTransaction().rollback();
        }

    }
}
