package be.pxl.student.JDBC;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.exception.AccountException;
import be.pxl.student.exception.AccountNotFoundException;


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

    private DAOManager daoManager;

    public AccountDAO(DAOManager daoManager) {
        this.daoManager = daoManager;
    }

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

        throw new AccountException("Not yet implemented");
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

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name"));
            } else {
                throw new AccountNotFoundException(String.format("Account with id [%d] not found", id));
            }

        } catch (SQLException e) {
            throw new AccountException(String.format("Exception while retrieving account with id [%d]", id), e);
        }
    }

    @Override
    public Account update(Account account) throws AccountException {

        /*try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(UPDATE)) {

            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setInt(3, account.getId());
            return preparedStatement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;*/
        throw new AccountException("yeet");
    }

    @Override
    public void delete(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }
}
