package dao;

import javafx.collections.ObservableList;
import models.Countries;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Allows users to interact with the Countries Table. This method allows users to interact with the countries table in the database.
 */
public interface CountriesDao {

    /**
     * Get countries from database. This method gets all the countries currently in the database. addCountry, updateCountry, and deleteCountry are not currently used but included for future development.
     *
     * @return all countries in database
     * @throws SQLException
     */
    ObservableList<Countries> getAllCountries() throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @throws SQLException
     */

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
    void addCountry(String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException;

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
    void updateCountry(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param countryId
     * @throws SQLException
     */
    void deleteCountry(int countryId) throws SQLException;

}
