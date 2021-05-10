package models;

import java.time.LocalDateTime;

/**
 * Creates first level division object
 */
public class FirstLevelDivisions {

    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;
    private String createdBy;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * First level division constructor.
     *
     * @param divisionId    division ID
     * @param division      division name
     * @param createDate    division create date
     * @param createdBy     division created by
     * @param lastUpdate    division last update date
     * @param lastUpdatedBy division last updated by
     * @param countryId     division country ID
     */
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }


    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Converts division name to string.
     *
     * @return division name
     */
    @Override
    public String toString() {
        return (getDivision());
    }
}
