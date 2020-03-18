package be.pxl.student.entity;

import java.sql.*;
import java.util.List;

public class AccountDAO implements DAO<Account, AccountException> {

    public static final String SELECT_BY_ID = "select * from Account where id = ?";
    private String url;

    public AccountDAO(String url) {
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public Account create(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public List<Account> getAll() throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public Account getById(int id) throws AccountException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()){
                return new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name"));
            } else{
                throw new AccountNotFoundException(String.format("Account with id [%d] not found", id));
            }

        } catch (SQLException e) {
            throw new AccountException(String.format("Exception while retrieving account with id [%d]", id), e);
        }
    }

    @Override
    public Account update(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }

    @Override
    public Account delete(Account account) throws AccountException {
        throw new AccountException("Not yet implemented");
    }
}
