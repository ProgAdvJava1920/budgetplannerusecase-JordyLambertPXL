package be.pxl.student.entity;

import be.pxl.student.JDBC.AccountDAO;
import be.pxl.student.JDBC.DAOManager;
import be.pxl.student.exception.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {
    //Properties
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";
    DAOManager manager;
    AccountDAO dao;


    //Setup and TearDown
    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        dao = new AccountDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }


    //Tests
    @Test
    void create_should_create_new_account_with_id_3() throws AccountException {
        Account account = dao.create(new Account(3, "TestIban", "TestName"));
        assertEquals(account, dao.getById(3));
    }

    @Test
        //getAll => geeft maar 2 terug omdat we er 2 dummies hebben ingezet.
    void it_should_return_2_items() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals(2, accounts.size());
    }

    @Test
    void it_should_return_account_with_id_1() throws AccountException {
        Account acc = dao.getById(1);
        Account expected = new Account("dummyName", "dummyIBAN");
        assertEquals(expected, acc);
    }

    @Test
    void it_should_update_account_name_to_dummyAccount() throws AccountException {
        Account acc = dao.getById(1);
        assertEquals("dummyName", acc.getName());

        acc.setName("dummyAccount");
        dao.update(acc);

        acc = dao.getById(1);
        assertEquals("dummyAccount", acc.getName());
    }

    @Test
    void it_should_be_null() throws AccountException {
        Account acc = dao.getById(1);
        dao.delete(acc);
        assertNull(dao.getById(acc.getId()));
    }
}