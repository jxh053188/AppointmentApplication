package models;

import java.time.LocalDateTime;

/**
 * Countries Object
 */
public class Countries {

    private int countryID;

    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Countries constructor
     *
     * @param countryID     country ID
     * @param country       Country name
     * @param createDate    created date
     * @param createdBy     created by
     * @param lastUpdate    last updated date
     * @param lastUpdatedBy last updated by
     */
    public Countries(int countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Get country ID
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Set country ID
     *
     * @param countryID country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Get country name
     *
     * @return country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country name
     *
     * @param country country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get created datetime
     *
     * @return created datetime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Set created datetime
     *
     * @param createDate created datetime
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Get created by
     *
     * @return created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set created by
     *
     * @param createdBy created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get last update datetime
     *
     * @return last update datetime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set last update datetime
     *
     * @param lastUpdate last update datetime
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get last updated by
     *
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Set last updated by
     *
     * @param lastUpdatedBy last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Converts country name to string.
     *
     * @return country name.
     */
    @Override
    public String toString() {
        return (getCountry());
    }
}
