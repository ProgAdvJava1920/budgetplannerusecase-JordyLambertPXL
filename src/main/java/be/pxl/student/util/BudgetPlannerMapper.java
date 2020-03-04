package be.pxl.student.util;

import be.pxl.student.entity.Account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetPlannerMapper {

    //TODO move to property file
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    public List<Account> mapAccounts(List<String> accountLines) {
        List<Account> accountList = new ArrayList<>();

        for (String accountLine : accountLines) {
            Account account = mapDataLineToAccount(accountLine);
            if (!accountList.contains(account)) accountList.add(account);
        }
        return accountList;
    }

    public Account mapDataLineToAccount(String line) {
        String[] items = line.split(",");
        String name = items[0];
        String iban = items[1];


        return new Account(name, iban);
    }

    public Date convertToDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
