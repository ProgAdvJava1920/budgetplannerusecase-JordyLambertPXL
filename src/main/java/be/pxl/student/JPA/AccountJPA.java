package be.pxl.student.JPA;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.exception.AccountException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountJPA implements DAO<Account, AccountException> {

    EntityManager entityManager = null;

    public AccountJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account create(Account account) throws AccountException {
        throw new AccountException("Not implemented");
    }

    @Override
    public List<Account> getAll() throws AccountException {
        TypedQuery<Account> query = entityManager.createNamedQuery("findAll", Account.class);
        return query.getResultList();
    }

    @Override
    public Account getById(int id) throws AccountException {
        return entityManager.find(Account.class, id);
    }

    @Override
    public Account update(Account account) throws AccountException {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        return account;
    }

    @Override
    public void delete(Account account) throws AccountException {
        entityManager.remove(account);
    }
}
