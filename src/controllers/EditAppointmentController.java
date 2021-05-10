package controllers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointments;
import models.Contacts;
import models.Customers;
import models.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.time.ZonedDateTime.of;

/**
 * Edit appointments in the database. This method allows a user to edit the details of an existing appointment.
 */
public class EditAppointmentController implements Initializable {

    CustomersDao customersDao = new CustomersDaoImpl();
    Appointments appointments;
    ContactDao contactDao = new ContactDaoImpl();
    UserDao userDao = new UserDaoImpl();
    AppointmentsDao appointmentsDao = new AppointmentsDaoImpl();

    @FXML
    private TextField appointmentIdTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private ComboBox<Contacts> contactComboField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<LocalTime> startComboBox;

    @FXML
    private ComboBox<LocalTime> endComboBox;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private ComboBox<Users> userComboBox;

    @FXML
    private DatePicker datePicker;

    /**
     * Find contact name. This method finds the contacts name from the contact ID stored in the database.
     *
     * @param Id is the contact ID.
     * @return the appointment contact.
     * @throws SQLException
     */
    Contacts contactNameFromId(int Id) throws SQLException {
        ObservableList<Contacts> contacts = contactDao.getAllContacts();
        Contacts appContact = null;
        for (Contacts c : contacts) {
            if (Id == c.getContactId()) {
                appContact = c;
            }
        }
        return appContact;
    }

    /**
     * Finds customer name. This method will find the customer name from the ID stored in the database.
     *
     * @param Id is the customerID
     * @return customer Name
     * @throws SQLException
     */
    Customers customerNameFromId(int Id) throws SQLException {
        ObservableList<Customers> customers = customersDao.getAllCustomers();
        Customers appCustomer = null;
        for (Customers c : customers) {
            if (Id == c.getCustomerId()) {
                appCustomer = c;
            }
        }
        return appCustomer;
    }

    /**
     * Find username from ID. This method will return the username from the ID stored in the database.
     *
     * @param Id is the userID
     * @return username
     * @throws SQLException
     */
    Users userNameFromId(int Id) throws SQLException {
        ObservableList<Users> users = userDao.getAllUsers();
        Users user = null;
        for (Users u : users) {
            if (Id == u.getUserId()) {
                user = u;
            }
        }
        return user;
    }

    /**
     * Populates form with existing data. This method will populate the form with the data that exists in the database.
     *
     * @param appointments is the appointment selected from the appointments page.
     * @throws SQLException
     */
    @FXML
    void selectedAppointment(Appointments appointments) throws SQLException {
        this.appointments = appointments;
        appointmentIdTextField.setText(Integer.toString(appointments.getAppointmentId()));
        titleTextField.setText(appointments.getTitle());
        descriptionTextField.setText(appointments.getDescription());
        locationTextField.setText(appointments.getLocation());
        typeComboBox.setValue(appointments.getType());
        startComboBox.setValue(appointments.getStart().toLocalTime());
        endComboBox.setValue(appointments.getEnd().toLocalTime());
        datePicker.setValue(appointments.getStart().toLocalDate());

        Contacts contacts = contactNameFromId(appointments.getContactId());
        contactComboField.setValue(contacts);

        Customers customers = customerNameFromId(appointments.getCustomerId());
        customerIdComboBox.setValue(customers);

        Users users = userNameFromId(appointments.getUserId());
        userComboBox.setValue(users);


    }

    /**
     * Returns user to the main screen. This method will cancel any changes the user made and return the to the main screen.
     *
     * @param event clicking the cancel button.
     * @throws IOException
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel adding a customer?");
        alert.setHeaderText("Cancel editing appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves changes in the database. This method will perform a series of validation checks and if successful, save changes to the database.
     *
     * @param event clicking the submit button.
     * @throws SQLException
     */
    @FXML
    void onSubmit(ActionEvent event) throws SQLException {
        if (!weekendCheck(datePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot make appointments outside of operating hours.");
            alert.setHeaderText("Outside business hours.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (!hourCheck(startComboBox.getValue(), endComboBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please check start and end time.");
            alert.setHeaderText("Invalid appointment times.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() ||
                titleTextField.getText().isEmpty() || titleTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            alert.setHeaderText("Invalid or missing data in the form.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            try {
                LocalDate date = datePicker.getValue();
                LocalTime start = startComboBox.getValue();
                LocalTime end = endComboBox.getValue();
                LocalDateTime appStartDT = LocalDateTime.of(date, start);
                LocalDateTime appEndDT = LocalDateTime.of(date, end);
                Timestamp appStartTS = Timestamp.valueOf(appStartDT);
                Timestamp appEndTS = Timestamp.valueOf(appEndDT);

                String title = titleTextField.getText();
                String description = descriptionTextField.getText();
                String location = locationTextField.getText();
                String type = typeComboBox.getValue();
                int contact = contactComboField.getValue().getContactId();
                int user = userComboBox.getValue().getUserId();
                int customer = customerIdComboBox.getValue().getCustomerId();
                int appointmentId = Integer.parseInt(appointmentIdTextField.getText());

                if (!overlapCheck(customer, appStartDT, appEndDT)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Customer already has an appointment at this time.");
                    alert.setHeaderText("Overlapping appointment.");
                    Optional<ButtonType> result = alert.showAndWait();
                } else {
                    appointmentsDao.updateAppointment(appointmentId, title, description, location, type, appStartTS, appEndTS, customer, user, contact);
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    /**
     * Populate times in the combo boxes. This method will determine the operating hours in the local time zone of the user and add them to the combo boxes. All times are in 10 minute intervals.
     */
    void populateTimes() {
        ObservableList<LocalTime> startTime = FXCollections.observableArrayList();
        ObservableList<LocalTime> endTime = FXCollections.observableArrayList();

        LocalDate estDate = LocalDate.now(); //get date from date picker
        LocalTime estTime = LocalTime.of(8, 00);
        ZoneId estZoneId = ZoneId.of("US/Eastern");
        ZonedDateTime estZDT = of(estDate, estTime, estZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant toUtc = estZDT.toInstant();
        ZonedDateTime estToLocalZDT = estZDT.withZoneSameInstant(localZoneId);

        for (int i = 0; i < 85; i++) {
            startTime.add(LocalTime.from(estToLocalZDT));
            endTime.add(LocalTime.from(estToLocalZDT));
            estToLocalZDT = estToLocalZDT.plusMinutes(10);
        }
        startComboBox.setItems(startTime);
        endComboBox.setItems(endTime);
    }

    /**
     * Generate appointment types. This method generates the appointment types and sets them to a combo box.
     */
    void types() {
        ObservableList<String> types = FXCollections.observableArrayList(
                "Sales",
                "Marketing",
                "Customer Service",
                "Status Update",
                "Other"
        );
        typeComboBox.setItems(types);

    }

    /**
     * Checks if appointment is on the weekend. This method will check to make sure that an appointment is not being scheduled on the weekend or in the past.
     *
     * @param localDate is the date of the appointment.
     * @return boolean.
     * @throws SQLException
     */
    @FXML
    boolean weekendCheck(LocalDate localDate) throws SQLException {
        LocalDate pastCheck = datePicker.getValue();
        String day = datePicker.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        if (day.contains("Saturday") || day.contains("Sunday") || pastCheck.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    /**
     * Checks hours are valid. This method will check to make sure start and end times are valid. Start times cannot be after end times and end times cannot be before start times.
     *
     * @param start is the LocalTime start of an appointment.
     * @param end   is the LocalTime end of an appointment.
     * @return boolean.
     * @throws SQLException
     */
    @FXML
    boolean hourCheck(LocalTime start, LocalTime end) throws SQLException {
        LocalTime appStart = startComboBox.getValue();
        LocalTime appEnd = endComboBox.getValue();
        if (appEnd.isBefore(appStart) || appStart.isAfter(appEnd) || appStart.equals(appEnd)) {
            return false;
        }
        return true;
    }

    /**
     * Creates list of appointments for specific customer. This method will generate a list of appointments for a specific customer based off the customer ID.
     *
     * @param Id is the customer ID
     * @return list of customer appointments.
     * @throws SQLException
     */
    ObservableList<Appointments> customerAppointments(int Id) throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        ObservableList<Appointments> customerAppointment = FXCollections.observableArrayList();

        for (Appointments a : allAppointments) {
            if (Id == a.getCustomerId())
                customerAppointment.add(a);
        }

        return customerAppointment;
    }

    /**
     * Checks for overlapping appointments. This method will compare the current appointment with the list of appointments for the specific customer to ensure there are no conflicts.
     *
     * @param Id    is the CustomerID
     * @param start is the appointment start time.
     * @param end   is the appointment end time.
     * @return boolean
     * @throws SQLException
     */
    @FXML
    boolean overlapCheck(int Id, LocalDateTime start, LocalDateTime end) throws SQLException {
        Id = customerIdComboBox.getValue().getCustomerId();
        ObservableList<Appointments> customerApps = customerAppointments(Id);

        for (Appointments a : customerApps) {
            if (a.getAppointmentId() != Integer.parseInt(appointmentIdTextField.getText())) {
                if (start.isAfter(a.getStart()) && start.isBefore(a.getEnd()) ||
                        end.isAfter(a.getStart()) && end.isBefore(a.getEnd()) ||
                        start.isBefore(a.getStart()) && end.isAfter(a.getEnd()) ||
                        start.equals(a.getStart()) && end.equals(a.getEnd()) ||
                        start.equals(a.getStart()) || end.equals(a.getEnd())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Initializes form. This method will populate the combo boxes with choices for the user.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerIdComboBox.setItems(customersDao.getAllCustomers());
            populateTimes();
            contactComboField.setItems(contactDao.getAllContacts());
            userComboBox.setItems(userDao.getAllUsers());
            types();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}
