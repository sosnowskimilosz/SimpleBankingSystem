package banking.SQLite;

import banking.CardNumberGenerator;
import banking.CollectorDataFromUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteTransferOfMoney {

    static String url = "jdbc:sqlite:";
    static String fileName;

    public static void doTransfer(String cardNumber) {
        System.out.println("\nTransfer");
        System.out.println("Enter card number:");
        String cardNumberWhereMoneyAreTransferred = CollectorDataFromUser.getTextFromUser();
        if (isCardNumberNotValidated(cardNumberWhereMoneyAreTransferred)) {
            showMsgAboutCardNumberWithMistake();
            return;
        } else if (cardNumberWhereMoneyAreTransferred.equals(cardNumber)) {
            showMsgTheSameNumberAccount();
            return;
        } else if (!SQLiteConnection.isNumberExistsInDB(cardNumberWhereMoneyAreTransferred)) {
            showMsgAboutExistingCardNumberInDB();
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        String howMuchMoneyUserWantsToTransfer = CollectorDataFromUser.getTextFromUser();
        if (SQLiteConnection.getBalanceOfAccount(cardNumber) < Integer.parseInt(howMuchMoneyUserWantsToTransfer)) {
            showMsgAboutNotEnoughMoney();
            return;
        }
        makeTransfer(cardNumber,cardNumberWhereMoneyAreTransferred,Integer.parseInt(howMuchMoneyUserWantsToTransfer));
        System.out.println("Success!");
    }

    public static void makeTransfer(String from, String where, int howMuch) {

        String reduceMoneySqlUpdate = "UPDATE card SET balance=balance-? WHERE number=?";

        String addMoneySqlUpdate = "UPDATE card SET balance=balance+? WHERE number=?";

        try (Connection conn = DriverManager.getConnection(url + fileName)) {

            conn.setAutoCommit(false);

            try (PreparedStatement preparedStatementOfReducing = conn.prepareStatement(reduceMoneySqlUpdate);
                 PreparedStatement preparedStatementOfAdding = conn.prepareStatement(addMoneySqlUpdate)) {
                preparedStatementOfReducing.setInt(1,howMuch);
                preparedStatementOfReducing.setString(2,from);
                preparedStatementOfReducing.executeUpdate();

                preparedStatementOfAdding.setInt(1,howMuch);
                preparedStatementOfAdding.setString(2,where);
                preparedStatementOfAdding.executeUpdate();

                conn.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static boolean isCardNumberNotValidated(String cardNumberWhereMoneyAreTransferred) {
        boolean result = true;
        if (cardNumberWhereMoneyAreTransferred.length() != 16) {
            return result;
        }
        int[] arrayToCheck = converStringNumberToIntArray(cardNumberWhereMoneyAreTransferred);
        CardNumberGenerator.multiplyOddDigitsBy2(arrayToCheck);
        CardNumberGenerator.subtract9ToNumbersOver9(arrayToCheck);
        if (CardNumberGenerator.findLastDigit(arrayToCheck) != Integer.parseInt(String.valueOf(cardNumberWhereMoneyAreTransferred.charAt(15)))) {
            return result;
        }
        return false;
    }

    public static int[] converStringNumberToIntArray(String cardNumberWhereMoneyAreTransferred) {
        int[] arrayToCheck = new int[16];
        for (int i = 0; i < 15; i++) {
            arrayToCheck[i] = Integer.parseInt(String.valueOf(cardNumberWhereMoneyAreTransferred.charAt(i)));
        }
        return arrayToCheck;
    }

    public static void showMsgAboutCardNumberWithMistake() {
        System.out.println("Probably you made mistake in the card number. Please try again!");
    }

    public static void showMsgAboutExistingCardNumberInDB() {
        System.out.println("Such a card does not exist.");
    }

    public static void showMsgAboutNotEnoughMoney() {
        System.out.println("Not enough money!");
    }

    private static void showMsgTheSameNumberAccount() {
        System.out.println("You can't transfer money to the same account!");
    }

    public static void setFileName(String fileName) {
        SQLiteTransferOfMoney.fileName = fileName;
    }
}
