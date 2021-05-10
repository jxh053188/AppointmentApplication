package models;

import java.time.LocalDateTime;

/**
 * Creates the appointments object.
 */
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Constructor. This is one constructor used for appointments.
     *
     * @param appointmentId appointment ID
     * @param title         appointment title
     * @param description   appointment description
     * @param location      appointment location
     * @param type          appointment type
     * @param start         appointment start datetime
     * @param end           appointment end datetime
     * @param createDate    appointment create date
     * @param createdBy     appointment created by
     * @param lastUpdate    appointment update date
     * @param lastUpdatedBy appointment updated by
     * @param customerId    appointment customer ID
     * @param userId        appointment userID
     * @param contactId     appointment contactID
     * @param contactName   appointment contact name.
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Second creator. This is the second constructor for appointments.
     *
     * @param appointmentId appointment ID
     * @param title         appointment title
     * @param description   appointment description
     * @param location      appointment location
     * @param contact       appointment contact
     * @param type          appointment type
     * @param start         appointment start datetime
     * @param end           appointment end datetime
     * @param customerId    appointment customer ID
     * @param userId        appointment user ID
     * @param contactId     appointment contact ID
     */
    public Appointments(int appointmentId, String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }

    /**
     * Gets appointment ID
     *
     * @return appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets appointment title
     *
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets appointment title
     *
     * @param title appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets appointment description
     *
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets appointment description
     *
     * @param description appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets appointment location.
     *
     * @return appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets appointment location
     *
     * @param location appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get appointment type
     *
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Set appointment type
     *
     * @param type appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get start time
     *
     * @return appointment start datetime
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Set start time
     *
     * @param start appointment start datetime.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Get end datetime
     *
     * @return end datetime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Set end datetime
     *
     * @param end end datetime
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Get creation datetime
     *
     * @return creation datetime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Set create datetime
     *
     * @param createDate create datetime
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
     * Get Last updated datetime
     *
     * @returnLast updated datetime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set Last updated datetime
     *
     * @param lastUpdate Last updated datetime
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
     * Get customer ID
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Set customer ID
     *
     * @param customerId customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Get user ID
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user ID
     *
     * @param userId user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get contact ID
     *
     * @return contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Set contact ID
     *
     * @param contactId contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Get contact name
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Set contact name
     *
     * @param contactName contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}

