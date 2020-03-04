package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetPlannerMapper {
    //TODO move and use properties file
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    public static final int CVS_ITEM_COUNT = 7;
    private Map<String, Account> accountMap = new HashMap<>();

    public List<Account> mapAccounts(List<String> accountLines) {

        for (String accountLine : accountLines) {
            try {
                Account account = mapDataLineToAccount(accountLine);
                accountMap.putIfAbsent(account.getIBAN(), account);

            } catch (ParseException | BudgetPlannerException e) {
                System.err.printf("Could not parse line [%s]%n", accountLine);

            }
        }
        return new ArrayList<>(accountMap.values());
    }

    public Account mapDataLineToAccount(String line) throws BudgetPlannerException, ParseException {
        String[] items = line.split(",");

        if (items.length != CVS_ITEM_COUNT)
            throw new BudgetPlannerException(String.format("Not enough elements. Expected %d, found %s", CVS_ITEM_COUNT, items.length));

        String name = items[0];
        String iban = items[1];

        Account account = accountMap.getOrDefault(iban, new Account(name, iban));
        Payment p = mapItemsToPayment(items);
        account.getPayments().add(p);

        return account;
    }

    public Date convertToDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public Payment mapItemsToPayment(String[] items) throws ParseException {
        return new Payment(
                items[2],                       //IBAN
                convertToDate(items[3]),        //Transaction date
                Float.parseFloat(items[4]),     //Amount
                items[5],                       //Currency
                items[6]                        //Detail
        );
    }
}