package banking.SQLite;

import banking.CollectorDataFromUser;

import java.sql.*;

public class
SQLiteConnection {

    static String url = "jdbc:sqlite:";
    static String fileName;
    static int maxId = 0;

    public static void setFileName(String fileName) {
        SQLiteConnection.fileName = fileName;
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url + fileName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static boolean isNumberExistsInDB(String numberToCheck) {
        String sql = "SELECT * FROM card WHERE number='" + numberToCheck + "'";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean isDataOfAccountValidated(String number, String pin) {
        String sql = "SELECT * FROM card WHERE number='" + number + "' AND pin='" + pin + "';";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void createNewTable(String name) {
        String sql = "CREATE TABLE IF NOT EXISTS " + name + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	number text NOT NULL,\n"
                + "	pin text NOT NULL,\n"
                + "	balance integer default 0\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertDataToDB(String number, String pin) {
        String sql = "INSERT INTO card(id,number,pin,balance) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            maxId = getLastId() + 1;
            pstmt.setInt(1, maxId);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showBalance(String cardNumber, String pin) {
        String sql = "SELECT balance"
                + " FROM card WHERE number='" + cardNumber + "'"
                + " AND pin='" + pin + "';";
        int balance = 0;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nBalance: " + balance);
    }

    public static void deleteAccount(String cardNumber, String pin) {
        String sql = "DELETE"
                + " FROM card WHERE number='" + cardNumber + "'"
                + " AND pin='" + pin + "';";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            if (statement.executeUpdate(sql) == 1) {
                System.out.println("\nThe account has been closed!\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addIncomeToAccount(String cardNumber, String pin) {
        System.out.println("\nEnter income:");
        int income = Integer.parseInt(CollectorDataFromUser.getTextFromUser());
        String sql = "UPDATE card SET balance=balance+" + income
                + " WHERE number='" + cardNumber + "'"
                + " AND pin='" + pin + "';";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            if (statement.executeUpdate(sql) == 1) {
                System.out.println("Income was added!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getLastId() {
        String sql = "SELECT id AS max_id"
                + " FROM card";
        int returned = 0;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                returned = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returned;
    }

    public static int getBalanceOfAccount(String numberOfAccount) {
        String sql = "SELECT balance FROM card WHERE number='" + numberOfAccount + "';";
        int returned=0;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                returned = rs.getInt("balance");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return returned;
    }
}
