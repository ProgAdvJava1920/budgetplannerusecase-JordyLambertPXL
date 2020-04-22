package be.pxl.student.JPA;

import be.pxl.student.entity.*;
import be.pxl.student.exception.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountJPATest {
//TODO fix table account not found!!!

    DAO<Account, AccountException> dao;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new AccountJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    //Tests
    @Test
    void create() {
        fail("not imp");
    }

    @Test
    void getAll() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals(2, accounts.size());
    }

    @Test
    void getById() throws AccountException {
        Account acc = dao.getById(1);
        Account expected = new Account("dummyName", "dummyIBAN");
        assertEquals(expected, acc);
    }

    @Test
    void update() throws AccountException {
        String updateName = "seog,sf";
        Account account = dao.getById(1);
        account.setName(updateName);
        dao.update(account);
        entityManager.clear();

        assertEquals(updateName, dao.getById(1).getName());

    }

    @Test
    void delete() throws AccountException {
        Account dummyAcc = new Account(1, "dummyIban", "dummyName");
        dao.delete(dummyAcc);
        assertNull(dao.getById(1));
    }
}