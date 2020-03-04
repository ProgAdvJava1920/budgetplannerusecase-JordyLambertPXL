package be.pxl.student;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BudgetPlanner {

    //private static final Logger logger = LogManager.getLogger(BudgetPlanner.class);

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/account_payments.csv");
    }
}