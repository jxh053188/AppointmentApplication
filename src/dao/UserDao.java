package dao;

import javafx.collections.ObservableList;
import models.Users;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Allows users to interact with the users table in the database. Allows users to interact with users table in the database. Add, update and delete are not currently used but included for future development.
 */
public interface UserDao {

    /**
     * Gets users from the database. This method gets all users from the database.
     *
     * @return all users
     * @throws SQLException
     */
    ObservableList<Users> getAllUsers() throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param userName      username
     * @param password      password
     * @param createDate    create date
     * @param createdBy     created by
     * @param lastUpdate    last updated date
     * @param lastUpdatedBy last updated by
     * @throws SQLException
     */
    void addUser(String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param userId        user id
     * @param userName      user name
     * @param password      password
     * @param createDate    created date
     * @param createdBy     created by
     * @param lastUpdate    last update date
     * @param lastUpdatedBy last updatedby
     * @throws SQLException
     */
    void updateUser(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param userId userId
     * @throws SQLException
     */
    void deleteUser(int userId) throws SQLException;

}
