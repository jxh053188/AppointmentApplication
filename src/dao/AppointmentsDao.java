package dao;

import javafx.collections.ObservableList;
import models.Appointments;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Allows users to interact with the appointments table in the database.
 */
public interface AppointmentsDao {

    /**
     * Gets all appointments. This method gets all appointments from the database.
     *
     * @return list of all appointments.
     * @throws SQLException
     */
    ObservableList<Appointments> getAllAppointments() throws SQLException;

    /**
     * Creates a new appointment. This method creates a new appointment for the database.
     *
     * @param title       appointment title
     * @param description appointment description
     * @param location    appointment location
     * @param type        appointment type
     * @param start       appointment start time
     * @param end         appointment end time
     * @param customerId  appointment customer ID
     * @param userId      appointment user ID
     * @param contactId   appointment Contact ID
     * @throws SQLException
     */
    void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException;

    /**
     * Updates existing appointment. This method updates an existing appointment in the database.
     *
     * @param appointmentId appointment ID
     * @param title         appointment Title
     * @param description   appointment description
     * @param location      appointment location
     * @param type          appointment type
     * @param start         appointment start
     * @param end           appointment end
     * @param customerId    appointment customer ID
     * @param userId        appointment user ID
     * @param contactId     appointment contact ID
     * @throws SQLException
     */
    void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException;

    /**
     * Deletes appointment. This method deletes an appointment based on the appointment ID.
     *
     * @param appointmentId appointment ID
     * @throws SQLException
     */
    void deleteAppointment(int appointmentId) throws SQLException;

}
