package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Controls the database connection.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ08b2Q?serverTimezone=UTC";

    private static final String jdbcURL = protocol + vendorName + ipAddress;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U08b2Q";
    private static final String password = "53689241770";

    /**
     * Starts database connection. This method begins the database connection.
     *
     * @return database connection.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("Connections Successful!");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Close database connection. This method closes the database connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get database connection.
     *
     * @return database connection.
     */
    public static Connection getConnection() {
        return conn;
    }

}
