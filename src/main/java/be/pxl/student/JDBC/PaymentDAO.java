package be.pxl.student.JDBC;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.exception.PaymentException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements DAO<Payment, PaymentException> {
    //Statements
    public static final String SELECT_BY_ID =
            "SELECT * FROM Payment WHERE id = ?";
    public static final String SELECT_ALL =
            "SELECT * FROM Payment;";
    public static final String INSERT_PAYMENT =
            "INSERT INTO Payment (`date`, `amount`, `currency`, `detail`, `accountId`, `counterAccountId`) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE Payment SET date=?, amount =?, currency =?, detail =? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM Payment WHERE id = ?";


    //Properties
    private DAOManager daoManager;


    //Constructor
    public PaymentDAO(DAOManager daoManager) {
        this.daoManager = daoManager;
    }


    //CRUD
    @Override
    public Payment create(Payment payment) throws PaymentException {
        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(INSERT_PAYMENT)) {

            //region Set parameters
            preparedStatement.setDate(1, (Date) payment.getDate());
            preparedStatement.setFloat(2, payment.getAmount());
            preparedStatement.setString(3, payment.getCurrency());
            preparedStatement.setString(4, payment.getDetail());
            preparedStatement.setInt(5, payment.getAccount().getId());
            preparedStatement.setInt(6, payment.getCounterAccount().getId());
            //endregion

            int result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.first()) {
                int id = generatedKeys.getInt(1);
                payment.setId(id);
            }

            if (result == 1) {
                return payment;
            }

            daoManager.commit();
        } catch (SQLException e) {
            daoManager.rollback(e);
            throw new PaymentException(String.format("Error creating payment [%s]", payment), e);
        }
        return null;
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        List<Payment> paymentList = new ArrayList<>();

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                paymentList.add(new Payment(
                        resultSet.getDate("date"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("currency"),
                        resultSet.getString("detail")));
            }
        } catch (SQLException e) {
            throw new PaymentException("Error retrieving payments", e);
        }
        return paymentList;
    }

    @Override
    public Payment getById(int id) throws PaymentException {
        Payment payment = null;

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                payment = new Payment(
                        resultSet.getDate("date"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("currency"),
                        resultSet.getString("detail"));

                Account account = new Account();
                Account counterAccount = new Account();
                int accountId = resultSet.getInt("accountId");
                int counterAccountId = resultSet.getInt("counterAccountId");
                account.setId(accountId);
                counterAccount.setId(counterAccountId);
                payment.setId(id);
                payment.setAccount(account);
                payment.setCounterAccount(counterAccount);
            }


        } catch (SQLException e) {
            throw new PaymentException(String.format("Exception while retrieving Payment with id [%d]", id), e);
        }
        return payment;
    }

    @Override
    public Payment update(Payment payment) throws PaymentException {
        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(UPDATE)) {
            preparedStatement.setDate(1, (Date) payment.getDate());
            preparedStatement.setFloat(2, payment.getAmount());
            preparedStatement.setString(3, payment.getCurrency());
            preparedStatement.setString(4, payment.getDetail());
            preparedStatement.setInt(5, payment.getAccount().getId());
            preparedStatement.setInt(6, payment.getCounterAccount().getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected != 1) throw new PaymentException("Payment more or less than one row updated!");
        } catch (SQLException e) {
            throw new PaymentException("Error updating payments", e);
        }
        return payment;
    }

    @Override
    public void delete(Payment payment) throws PaymentException {
        int rows = 0;

        try (PreparedStatement preparedStatement = daoManager.getConnection().prepareStatement(DELETE)) {
            preparedStatement.setInt(1, payment.getId());
            rows = preparedStatement.executeUpdate();

            if (rows != 1) throw new PaymentException(String.format("Rows affected should be one but is %d", rows));
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}