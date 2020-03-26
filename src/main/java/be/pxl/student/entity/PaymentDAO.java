package be.pxl.student.entity;

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
    public boolean update(Payment payment) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }

    @Override
    public String delete(Payment payment) throws PaymentException {
        throw new PaymentException("Not yet implemented");
    }
}