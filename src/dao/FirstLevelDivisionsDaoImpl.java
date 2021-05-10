package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FirstLevelDivisions;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements functions for FirstLevelDivisionsDao. This method implements the functions used to interact with first level divisions in the database.
 */
public class FirstLevelDivisionsDaoImpl implements FirstLevelDivisionsDao {
    /**
     * This method returns all the first level divisions in the database. This method returns first level divisions from the database using a prepared statement.
     *
     * @return all first level divisions
     * @throws SQLException
     */
    @Override
    public ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> firstLevelDivisions = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM first_level_divisions";

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            int divisionId = resultSet.getInt("Division_ID");
            String division = resultSet.getString("Division");
            LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int countryId = resultSet.getInt("Country_ID");

            FirstLevelDivisions FLDResult = new FirstLevelDivisions(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            firstLevelDivisions.add(FLDResult);
        }
        return firstLevelDivisions;

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param division      division name
     * @param createDate    create date
     * @param lastUpdate    updated date
     * @param createdBy     created by
     * @param lastUpdatedBy last updated by
     * @param countryId     country ID
     * @throws SQLException
     */
    @Override
    public void addFirstLevelDivision(String division, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy, int countryId) throws SQLException {

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param divisionId    division ID
     * @param division      division name
     * @param createDate    created date
     * @param lastUpdate    last updated by
     * @param createdBy     created by
     * @param lastUpdatedBy last updated by
     * @param countryId     country ID
     * @throws SQLException
     */
    @Override
    public void updateFirstLevelDivision(int divisionId, String division, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy, int countryId) throws SQLException {

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param divisionId division ID
     * @throws SQLException
     */
    @Override
    public void deleteFirstLevelDivision(int divisionId) throws SQLException {

    }
}

