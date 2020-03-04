package be.pxl.student.util;

import be.pxl.student.entity.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    public static List<String> readFile(Path p) {

        Account account = new Account();
        List<String> csvLines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line = reader.readLine();        //Skip first line of document

            while ((line = reader.readLine()) != null) {
                csvLines.add(line);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return csvLines;
    }
}

