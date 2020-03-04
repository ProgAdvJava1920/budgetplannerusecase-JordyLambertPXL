package be.pxl.student.util;

import be.pxl.student.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetPlannerMapperTest {
    Path p = Paths.get(System.getProperty("user.dir")).resolve("src/test/resources/account_payments_test.csv");
    BudgetPlannerMapper mapper;
    List<String> accountLines;
    String testDataLine = "Jos,BE69771770897312,BE17795215960626,Thu Feb 13 05:47:35 CET 2020,265.8,EUR,Ut ut necessitatibus itaque ullam.";

    @BeforeEach
    void setUp() {
        accountLines = BudgetPlannerImporter.readFile(p);
        mapper = new BudgetPlannerMapper();
    }

    @Test
    void it_should_return_non_empty_list() {
        assertFalse(mapper.mapAccounts(accountLines).isEmpty());
    }

    @Test
    void it_should_map_to_account_list_with_1_account() {
        List<Account> accountList = mapper.mapAccounts(accountLines);
        assertEquals(1, accountList.size(), "Should have one account.");
    }

    @Test
    void it_should_map_to_account_list_with_1_account_with_2_payments() {
        List<Account> accountList = mapper.mapAccounts(accountLines);
        assertEquals(2, accountList.get(0).getPayments().size(), "Account should have two payments.");
    }

    @Test
    void it_should_map_line_to_account_object() {
        Account expectedAccount = new Account("Jos", "BE69771770897312");
        Account lineToAccount = mapper.mapDataLineToAccount(testDataLine);
        assertEquals(expectedAccount, lineToAccount);
    }
}