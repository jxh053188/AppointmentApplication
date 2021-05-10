package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customers;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements CustomerDao functions. This method implements the functions used in the CustomerDao.
 */
public class CustomersDaoImpl implements CustomersDao {
    /**
     * Get all users. This method returns all users in the database using a prepared statement.
     *
     * @return all users.
     * @throws SQLException
     */
    @Override
    public ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM customers \n" + "JOIN first_level_divisions \n" + "ON customers.Division_ID = first_level_divisions.Division_ID";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            int customerId = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phone = resultSet.getString("Phone");
            int divisionId = resultSet.getInt("Division_ID");
            String division = resultSet.getString("Division");

            Customers newCustomer = new Customers(customerId, customerName, address, postalCode, phone, divisionId, division);
            customers.add(newCustomer);
        }

        return customers;
    }

    /**
     * Create new customer. This method creates a new customer in the database using a prepared statement.
     *
     * @param customerName customer name
     * @param address      customer address
     * @param postalCode   customer postal code
     * @param phone        customer phone number
     * @param divisionId   customer division ID
     * @throws SQLException
     */
    @Override
    public void addCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String insertString = ("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)");
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertString);
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, "admin");
        preparedStatement.setString(6, "admin");
        preparedStatement.setInt(7, divisionId);

        preparedStatement.execute();

    }

    /**
     * Updates customer in the database. This method updates customer information in the database using a prepared statement.
     *
     * @param customerId   customer Id
     * @param customerName customer Name
     * @param address      customer address
     * @param postalCode   customer postal code
     * @param phone        customer phone number
     * @param divisionId   customer division id
     * @throws SQLException
     */
    @Override
    public void updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateStatement);
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, divisionId);
        preparedStatement.setInt(6, customerId);

        preparedStatement.execute();

    }

    /**
     * Deletes a customer from the database. This method deletes a customer from the database using a prepared statement.
     *
     * @param customerId customerID
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteStatement);
        preparedStatement.setInt(1, customerId);

        preparedStatement.execute();

    }
}
