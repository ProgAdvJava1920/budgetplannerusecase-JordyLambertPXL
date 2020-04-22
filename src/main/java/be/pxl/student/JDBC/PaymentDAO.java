package be.pxl.student.JDBC;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.exception.PaymentException;

import java.util.List;

public class PaymentDAO implements DAO<Payment, PaymentException> {
    // TODO: implement PaymentDAO

    private DAOManager daoManager;

    public PaymentDAO(DAOManager daoManager) {
        this.daoManager = daoManager;
    }

    @Override
    public Payment create(Payment payment) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }

    @Override
    public Payment getById(int id) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }

    @Override
    public Account update(Payment payment) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }

    @Override
    public void delete(Payment payment) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }
}