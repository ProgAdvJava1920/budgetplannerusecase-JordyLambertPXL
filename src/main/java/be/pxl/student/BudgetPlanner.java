package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerException;
import be.pxl.student.util.BudgetPlannerImporter;
import be.pxl.student.util.BudgetPlannerMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BudgetPlanner {

    private static Logger logger = LogManager.getLogger(BudgetPlanner.class);

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/account_payments.csv");
        logger.info("Starting csv import");
        List<String> list = BudgetPlannerImporter.readFile(path);
        logger.info("import done");
        logger.info("Starting account mapping.");
        List<Account> accounts = new BudgetPlannerMapper().mapAccounts(list);
        accounts.forEach(logger::debug);
        logger.info("Accoutn mapping done");
    }
}