package dao;

import javafx.collections.ObservableList;
import models.Contacts;

import java.sql.SQLException;

/**
 * Interact with contact table. This interface allows a user to interact with the Contact table in the database. addContact, updateContact, and deleteContact are not currently used in the application but included for future development.
 */
public interface ContactDao {

    /**
     * Gets all contacts. This method gets all contacts from the database.
     *
     * @return
     * @throws SQLException
     */
    ObservableList<Contacts> getAllContacts() throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactName contact name
     * @param email       contact email
     * @throws SQLException
     */
    void addContact(String contactName, String email) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactId   contact ID
     * @param contactName contact Name
     * @param email       contact email
     * @throws SQLException
     */
    void updateContact(int contactId, String contactName, String email) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param contactId contact ID
     * @throws SQLException
     */
    void deleteContact(int contactId) throws SQLException;

}
