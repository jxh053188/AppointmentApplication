package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contacts;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements functions for the ContactsDao. This method implements all the functions of the ContactsDao.
 */
public class ContactDaoImpl implements ContactDao {


    /**
     * Returns contacts. This method returns all contacts from the databse.
     *
     * @return all contacts
     * @throws SQLException
     */
    @Override
    public ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM contacts";

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            int contactId = resultSet.getInt("Contact_ID");
            String name = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            Contacts allContacts = new Contacts(contactId, name, email);
            contacts.add(allContacts);
        }

        return contacts;
    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactName contact name
     * @param email       contact email
     * @throws SQLException
     */
    @Override
    public void addContact(String contactName, String email) throws SQLException {
        String insertStatement = "INSERT INTO contacts (Contact_ID, Contact_Name, Email)\n" +
                "VALUES (?, ?);";

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertStatement);
        preparedStatement.setString(1, contactName);
        preparedStatement.setString(2, email);

        preparedStatement.execute();

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactId   contact ID
     * @param contactName contact Name
     * @param email       contact email
     * @throws SQLException
     */
    @Override
    public void updateContact(int contactId, String contactName, String email) throws SQLException {
        String updateStatement = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateStatement);

        preparedStatement.setString(1, contactName);
        preparedStatement.setString(2, email);
        preparedStatement.setInt(3, contactId);

        preparedStatement.execute();

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactId contact ID
     * @throws SQLException
     */
    @Override
    public void deleteContact(int contactId) throws SQLException {
        String deleteStatement = "DELETE FROM Contacts WHERE Contact_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteStatement);
        preparedStatement.setInt(1, contactId);
        preparedStatement.execute();

    }
}
