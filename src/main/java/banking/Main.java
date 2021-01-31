package banking;

import banking.SQLite.SQLiteConnection;
import banking.SQLite.SQLiteTransferOfMoney;

public class Main {
    public static void main(String[] args) {

        SQLiteConnection.setFileName("cards.s3db");
        SQLiteTransferOfMoney.setFileName("cards.s3db");
        Controller controller = new Controller();
    }
}
