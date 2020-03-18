package be.pxl.student.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AccountDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";

    @Test
    void create() {
        fail("not yet impl");
    }

    @Test
    void getAll() {

        fail("not yet impl");
    }

    @Test
    void it_should_return_account_with_id_1() throws AccountException {
        AccountDAO dao = new AccountDAO(DB_URL);
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