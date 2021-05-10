package models;

/**
 * Creates contacts object.
 */
public class Contacts {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Contacts constructor.
     *
     * @param contactId   contact ID
     * @param contactName contact name
     * @param email       contact email address
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
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

    /**
     * Get email address
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email address
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets contact name to string.
     *
     * @return contact name as string.
     */
    @Override
    public String toString() {
        return (getContactName());
    }
}
