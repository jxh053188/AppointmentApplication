package models;

import java.time.LocalDateTime;

/**
 * Creates user object
 */
public class Users {

    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Users constructor
     *
     * @param userId        user Id
     * @param userName      username
     * @param password      password
     * @param createDate    created date
     * @param createdBy     created by
     * @param lastUpdate    last updated date
     * @param lastUpdatedBy last updated by
     */
    public Users(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    // public Users(int userId, String userName, String password) {}

    /**
     * Get user Id
     *
     * @return user Id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user Id
     *
     * @param userId user Id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * +
     * Get username
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set username
     *
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get user password
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Get last updated date
     *
     * @return last updated date
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set last updated date
     *
     * @param lastUpdate last updated date
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
     * Convert username to string.
     *
     * @return
     */
    @Override
    public String toString() {
        return (getUserName());
    }
}
