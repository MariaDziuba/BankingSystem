package src;

import java.sql.*;

public class Sql {

    private static String url = "jdbc:sqlite:/Users/maria/Desktop/Projects/BankingSystem/base/";

    public static void createNewDatabase(String fileName) {

        url += fileName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Database error");
        }
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {

        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "id INTEGER, "
                + "number TEXT, "
                + "pin TEXT, "
                + "balance INTEGER DEFAULT 0);";

        try (Connection conn = DriverManager.getConnection(url);

             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String number, String pin, Integer balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll() {
        String sql = "SELECT * FROM card";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("number") + "\t" +
                                rs.getString("pin") + "\t" +
                                rs.getInt("balance"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean contains(String numberNeed, String pinNeed) {
        String sql = "SELECT number, pin FROM card WHERE number = " + numberNeed + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next() && (rs.getString("pin").equals(pinNeed)))
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean numberExist(String numberNeed) {
        String sql = "SELECT number FROM card WHERE number = " + numberNeed + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static int findCurrentBalance(String currentNumber) {
        String sql = "SELECT balance FROM card WHERE number = " + currentNumber + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next())
                return rs.getInt("balance");
            else {
                System.out.println("Error in searching balance.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void makeBalanceChange(String currentNumber, int newBalance) {
        String sql = "UPDATE card SET balance = " + newBalance + " WHERE number = " + currentNumber + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteRow(String currentNumber) {
        String sql = "DELETE FROM card WHERE number = " + currentNumber + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
