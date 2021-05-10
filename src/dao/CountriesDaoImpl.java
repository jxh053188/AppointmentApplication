package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Countries;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements functions of CountriesDao. This class implements the functions of the CountriesDao.
 */
public class CountriesDaoImpl implements CountriesDao {
    /**
     * Returns countries in the database. This method uses a prepared statement to return all countries in the database.
     *
     * @return all countries
     * @throws SQLException
     */
    @Override
    public ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM countries;";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            int countryId = resultSet.getInt("Country_ID");
            String country = resultSet.getString("Country");
            LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            Countries countryList = new Countries(countryId, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            countries.add(countryList);
        }
        return countries;
    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @throws SQLException
     */
    @Override
    public void addCountry(String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param countryId
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @throws SQLException
     */
    @Override
    public void updateCountry(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {

    }

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param countryId
     * @throws SQLException
     */
    @Override
    public void deleteCountry(int countryId) throws SQLException {

    }
}
