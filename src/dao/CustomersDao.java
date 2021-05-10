package dao;

import javafx.collections.ObservableList;
import models.Customers;

import java.sql.SQLException;

/**
 * Allows users to interact with customer table in the database.
 */
public interface CustomersDao {

    /**
     * Gets customers from the database. This method gets all customers from the database.
     *
     * @return all customers
     * @throws SQLException
     */
    ObservableList<Customers> getAllCustomers() throws SQLException;

    /**
     * Adds customer to the database. This method allows a user to add a new customer to the database.
     *
     * @param customerName customer name
     * @param address      customer address
     * @param postalCode   customer postal code
     * @param phone        customer phone number
     * @param divisionId   customer division ID
     * @throws SQLException
     */
    void addCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException;

    /**
     * Updates customer information. This method updates customer information in the database.
     *
     * @param customerId   customer Id
     * @param customerName customer Name
     * @param address      customer address
     * @param postalCode   customer postal code
     * @param phone        customer phone number
     * @param divisionId   customer division id
     * @throws SQLException
     */
    void updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException;

    /**
     * Deletes customer. This method deletes a customer from the database.
     *
     * @param customerId customerID
     * @throws SQLException
     */
    void deleteCustomer(int customerId) throws SQLException;

}
