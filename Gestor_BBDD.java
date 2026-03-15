import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Gestor_BBDD {

    public static Connection openConnection(Scanner scanner) {
        System.out.println("Connecting to database...");

        String env = "";
        while (true) {
            System.out.println("Are you at school or outside? (SCHOOL/HOME):");
            env = scanner.nextLine().trim();
            if (env.equalsIgnoreCase("school") || env.equalsIgnoreCase("home")) break;
            System.out.println("Invalid input. Type SCHOOL or HOME.");
        }

        String dbUrl = env.equalsIgnoreCase("school")
                ? "jdbc:oracle:thin:@//192.168.3.26:1521/XEPDB2"
                : "jdbc:oracle:thin:@//oracle.ilerna.com:1521/XEPDB2";

        System.out.println("Username:");
        String username = scanner.nextLine().trim();

        System.out.println("Password:");
        String password = scanner.nextLine();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            if (conn.isValid(5)) {
                System.out.println("Connected (" + env.toUpperCase() + ").");
            } else {
                System.out.println("Connection created but may not be valid.");
            }

            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle driver not found.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        return null;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    public static int insertRow(Connection conn, String query) {
        return runWriteQuery(conn, query, "INSERT");
    }

    public static int updateRow(Connection conn, String query) {
        return runWriteQuery(conn, query, "UPDATE");
    }

    public static int deleteRow(Connection conn, String query) {
        return runWriteQuery(conn, query, "DELETE");
    }

    public static ArrayList<LinkedHashMap<String, String>> fetchRows(Connection conn, String query) {
        ArrayList<LinkedHashMap<String, String>> rows = new ArrayList<>();

        if (conn == null) {
            System.out.println("No connection available.");
            return rows;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            while (rs.next()) {
                LinkedHashMap<String, String> row = new LinkedHashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnLabel(i), rs.getString(i));
                }
                rows.add(row);
            }

        } catch (SQLException e) {
            System.out.println("SELECT error: " + e.getMessage());
        }

        return rows;
    }

    public static void printRows(Connection conn, String query, String[] columns) {
        if (conn == null) {
            System.out.println("No connection available.");
            return;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("---- Row " + count + " ----");
                for (String col : columns) {
                    System.out.println(col + ": " + rs.getString(col));
                }
            }

            if (count == 0) System.out.println("No results found.");

        } catch (SQLException e) {
            System.out.println("SELECT error: " + e.getMessage());
        }
    }

    private static int runWriteQuery(Connection conn, String query, String type) {
        if (conn == null) {
            System.out.println("No connection available.");
            return 0;
        }

        try (Statement stmt = conn.createStatement()) {
            int affected = stmt.executeUpdate(query);
            System.out.println(type + " OK. Rows affected: " + affected);
            return affected;
        } catch (SQLException e) {
            System.out.println(type + " failed: " + e.getMessage());
            return 0;
        }
    }
