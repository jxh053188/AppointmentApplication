package dao;

import javafx.collections.ObservableList;
import models.FirstLevelDivisions;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Allows users to interact with first_level_divisions in the database. This method allows users to interact with the first level divisions table in the database. add, update, and delete are not used but included for future development.
 */
public interface FirstLevelDivisionsDao {

    /**
     * Return first level divisions. This method returns all first level divisions in the database.
     *
     * @return all first level divisions.
     * @throws SQLException
     */
    ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException;

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
    void addFirstLevelDivision(String division, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy, int countryId) throws SQLException;

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
    void updateFirstLevelDivision(int divisionId, String division, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy, int countryId) throws SQLException;

    /**
     * Not currently used. This method has been included for future development.
     *
     * @param divisionId division ID
     * @throws SQLException
     */
    void deleteFirstLevelDivision(int divisionId) throws SQLException;

}
