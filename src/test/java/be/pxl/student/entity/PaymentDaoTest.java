package be.pxl.student.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDaoTest {
    PaymentDao dao;
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";


    @Test
    void create() {
        fail("NYI");
    }

    @BeforeEach
    void setUp() {
        //dao = new PaymentDao(DB_URL);
    }

    @Test
    void getAll() {
        //dao
        fail("NYI");
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