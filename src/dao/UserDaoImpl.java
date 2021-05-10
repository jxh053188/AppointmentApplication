package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Users;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements functions for UserDao. This method implements the functions necessary for the UserDao.
 */
public class UserDaoImpl implements UserDao {
    /**
     * This method returns all users. This method returns all users from the database using a prepared statement.
     *
     * @return all users.
     * @throws SQLException
     */
    @Override
    public ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> users = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM users;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            int userId = resultSet.getInt("User_ID");
            String userName = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            Users allUsers = new Users(userId, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            users.add(allUsers);
        }

        return users;
    }

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
    @Override
    public void addUser(String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {

    }

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
    @Override
    public void updateUser(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param userId userId
     * @throws SQLException
     */
    @Override
    public void deleteUser(int userId) throws SQLException {
        String deleteStatement = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteStatement);
        preparedStatement.setInt(1, userId);
        preparedStatement.execute();

    }
}
