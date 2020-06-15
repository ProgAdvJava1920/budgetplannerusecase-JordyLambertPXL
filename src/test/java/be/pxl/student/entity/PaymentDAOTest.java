package be.pxl.student.entity;

import be.pxl.student.JDBC.AccountDAO;
import be.pxl.student.JDBC.DAOManager;
import be.pxl.student.JDBC.PaymentDAO;
import be.pxl.student.exception.AccountException;
import be.pxl.student.exception.PaymentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";
    DAOManager manager;
    PaymentDAO dao;

    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        dao = new PaymentDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }


    //Tests
    @Test
    void should_create_payment_between_two_accounts_with_id_3() throws PaymentException, SQLException, AccountException {
        AccountDAO dao2 = new AccountDAO(manager);
        Account acc1 = dao2.getById(1);
        Account acc2 = dao2.getById(2);

        Payment p = new Payment(Date.valueOf("2020-06-15"), 199.99f, "EUR", "Dit zijn details van payment 3");
        p.setAccount(acc1);
        p.setCounterAccount(acc2);

        dao.create(p);
        assertEquals(p, dao.getById(3));
    }

    @Test
    void it_should_have_a_size_of_2() throws PaymentException {
        List<Payment> payments = dao.getAll();
        assertEquals(2, payments.size());
    }

    @Test
    void getById() {
        fail("NYI");
    }

    @Test
    void update() {
        fail("NYI");
    }

    @Test
    void delete() {
        fail("NYI");
    }
}