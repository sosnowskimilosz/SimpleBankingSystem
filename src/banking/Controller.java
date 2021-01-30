package banking;

import banking.SQLite.SQLiteConnection;
import banking.SQLite.SQLiteTransferOfMoney;

public class Controller {

    boolean isAppTurningOn = true;
    boolean isManagingAccountTurningOn = false;

    Controller() {
        launchMainMenu();
    }

    public void launchMainMenu() {
        SQLiteConnection.createNewTable("card");
        while (isAppTurningOn) {
            printMainMenu();
            String userChoice = CollectorDataFromUser.getTextFromUser();
            while (!isUserInputMainMenuValidated(userChoice)) {
                userChoice = CollectorDataFromUser.getTextFromUser();
            }
            switch (userChoice) {
                case "1":
                    createAnAccount();
                    break;
                case "2":
                    isManagingAccountTurningOn = true;
                    logInAccount();
                    break;
                case "0":
                    isAppTurningOn = false;
                    break;
            }
        }
        System.out.println("\nBye!");
    }

    public void printMainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public void manageOfAccount(String cardNumber, String pin) {
        while (isManagingAccountTurningOn) {
            printManagingMenu();
            String userChoice = CollectorDataFromUser.getTextFromUser();
            while (!isUserInputAccountMenuValidated(userChoice)) {
                userChoice = CollectorDataFromUser.getTextFromUser();
            }
            switch (userChoice) {
                case "1":
                    SQLiteConnection.showBalance(cardNumber, pin);
                    break;
                case "2":
                    SQLiteConnection.addIncomeToAccount(cardNumber, pin);
                    break;
                case "3":
                    SQLiteTransferOfMoney.doTransfer(cardNumber);
                    break;
                case "4":
                    SQLiteConnection.deleteAccount(cardNumber, pin);
                    isManagingAccountTurningOn = false;
                    break;
                case "5":
                    showMsgAboutLogOut();
                    isManagingAccountTurningOn = false;
                    break;
                case "0":
                    isManagingAccountTurningOn = false;
                    isAppTurningOn = false;
                    break;
            }
        }
    }

    private void showMsgAboutLogOut() {
        System.out.println("\nYou have successfully logged out!\n");
    }

    private void showMsgAboutLogIn() {
        System.out.println("\nYou have successfully logged in!");
    }

    private void printManagingMenu() {
        System.out.println("\n1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    void logInAccount() {
        System.out.println("\nEnter your card number:");
        String inputCardNumber = CollectorDataFromUser.getTextFromUser();
        System.out.println("Enter your PIN:");
        String inputPin = CollectorDataFromUser.getTextFromUser();
        if (SQLiteConnection.isDataOfAccountValidated(inputCardNumber, inputPin)) {
            showMsgAboutLogIn();
            manageOfAccount(inputCardNumber, inputPin);
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    private void createAnAccount() {
        String newCardNumber = CardNumberGenerator.generateCardNumber();
        while (SQLiteConnection.isNumberExistsInDB(newCardNumber)) {
            newCardNumber = CardNumberGenerator.generateCardNumber();
        }
        String pin = PinGenerator.generatePin();
        SQLiteConnection.insertDataToDB(newCardNumber, pin);
        showInitializedInfo(newCardNumber, pin);
    }

    void showInitializedInfo(String cardNumber, String pin) {
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(pin + "\n");
    }

    public boolean isUserInputMainMenuValidated(String input) {
        return input.matches("[120]");
    }

    public boolean isUserInputAccountMenuValidated(String input) {
        return input.matches("[123450]");
    }
}
