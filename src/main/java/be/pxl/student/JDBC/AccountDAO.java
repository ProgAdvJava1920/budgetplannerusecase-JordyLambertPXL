package be.pxl.student.JDBC;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.exception.AccountException;
import be.pxl.student.exception.AccountNotFoundException;
import org.hibernate.cfg.NotYetImplementedException;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account, AccountException> {

    public static final String SELECT_BY_ID =
            "SELECT * FROM Account WHERE id = ?";
    public static final String SELECT_ALL =
            "SELECT * FROM Account;";
    public static final String INSERT_ACCOUNT =
            "INSERT INTO Account (`IBAN`, `name`) VALUES(?, ?)";
    private static final String UPDATE =
            "UPDATE Account SET IBAN=?, name =? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM Account WHERE id = ?";

    private DAOManager daoManager;

    //Constructor
    public AccountDAO(DAOManager daoManager) {
        this.daoManager = daoManager;
    }


    //CRUD
    @Override
    public Account create(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(INSERT_ACCOUNT);) {
            //preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getName());

            int result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int id = generatedKeys.getInt(1);
                account.setId(id);
            }

            if (result == 1) {
                return account;
            }
            daoManager.commit();
        } catch (SQLException e) {
            daoManager.rollback(e);
            throw new AccountException(String.format("Error creating account [%s]", account), e);
        }
        return null;
    }

    @Override
    public List<Account> getAll() throws AccountException {
        List<Account> accountList = new ArrayList<>();

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountList.add(new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name")));

            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving accounts", e);
        }

        return accountList;
    }

    @Override
    public Account getById(int id) throws AccountException {
        Account account = null;

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                account =  new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name"));
            }

        } catch (SQLException e) {
            throw new AccountException(String.format("Exception while retrieving account with id [%d]", id), e);
        }
        return account;
    }

    @Override
    public Account update(Account account) throws AccountException {

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(UPDATE)) {
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setInt(3, account.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected != 1) throw new AccountException("Account more or less than one row updated!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }

    @Override
    public void delete(Account account) throws AccountException {
        int rows = 0;

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(DELETE)) {
            preparedStatement.setInt(1, account.getId());
            rows = preparedStatement.executeUpdate();

            if (rows != 1) throw new AccountException(String.format("Rows affected should be one but is %d", rows));
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}
