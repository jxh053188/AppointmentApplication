package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointments;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implements functions for the AppointmentsDAO. This method implements all the functions for the AppointmentsDao.
 */
public class AppointmentsDaoImpl implements AppointmentsDao {

    /**
     * Returns all appointments. This method uses a prepared statement to return all appointments stored in the database.
     *
     * @return all appointments
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM appointments\n" +
                "JOIN contacts \n" +
                "ON appointments.Contact_ID = contacts.Contact_ID;";

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectStatement);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()) {
            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            String contact = resultSet.getString("Contact_Name");
            int contactId = resultSet.getInt("Contact_ID");

            Appointments appointmentsResults = new Appointments(appointmentId, title, description, location, contact, type, start, end, customerId, userId, contactId);
            appointments.add(appointmentsResults);

        }
        return appointments;
    }

    /**
     * Creates a new appointment. This method uses a prepared statement to create a new appointment in the database.
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
    @Override
    public void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertStatement);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start.toLocalDateTime()));
        preparedStatement.setTimestamp(6, end);
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, userId);
        preparedStatement.setInt(9, contactId);

        preparedStatement.execute();
    }


    /**
     * Updates existing appointment. This method updates an existing appointment in the database using a prepared statement.
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
    @Override
    public void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateStatement);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, start);
        preparedStatement.setTimestamp(6, end);
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, userId);
        preparedStatement.setInt(9, contactId);
        preparedStatement.setInt(10, appointmentId);

        preparedStatement.execute();

    }

    /**
     * Deletes appointment. This method deletes an appointment from the database using a prepared statement.
     *
     * @param appointmentId appointment ID
     * @throws SQLException
     */
    @Override
    public void deleteAppointment(int appointmentId) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(deleteStatement);
        preparedStatement.setInt(1, appointmentId);
        preparedStatement.execute();
    }
}
