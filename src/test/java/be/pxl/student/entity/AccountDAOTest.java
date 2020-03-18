package be.pxl.student.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AccountDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";
    DAOManager manager;
    AccountDAO dao;

    @Test
    void create() {
        fail("not yet impl");
    }

    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        dao = new AccountDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }

    @Test
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
    void update() {
        fail("not yet impl");
    }

    @Test
    void delete() {
        fail("not yet impl");
    }
}