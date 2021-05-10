package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a prepared statement object.
 */
public class DBQuery {

    private static PreparedStatement statement;

    /**
     * Sets the prepared statement.
     *
     * @param conn         Database connection
     * @param sqlStatement SQL statement
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     * Gets prepared statement
     *
     * @return prepared statement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
