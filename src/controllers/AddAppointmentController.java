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
 * Controls adding adding appoints. The AddAppointmentController contains all of the methods necessary to creat a new appointment in the database.
 */
public class AddAppointmentController implements Initializable {
    CustomersDao customersDao = new CustomersDaoImpl();
    AppointmentsDao appointmentsDao = new AppointmentsDaoImpl();
    ContactDao contactDao = new ContactDaoImpl();
    UserDao userDao = new UserDaoImpl();

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
     * Initializes start and end combo boxes with times. This method sets the business hours to the local time zone and loop through in 10 minute increments until the end of the business day.
     */
    void populateTimes() {
        ObservableList<LocalTime> startTime = FXCollections.observableArrayList();
        ObservableList<LocalTime> endTime = FXCollections.observableArrayList();

        LocalDate estDate = LocalDate.now(); //get date from date picker
        LocalTime estTime = LocalTime.of(8, 00);
        ZoneId estZoneId = ZoneId.of("US/Eastern");
        ZonedDateTime estZDT = of(estDate, estTime, estZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime estToLocalZDT = estZDT.withZoneSameInstant(localZoneId);

        for(int i = 0; i < 85; i++){
            startTime.add(LocalTime.from(estToLocalZDT));
            endTime.add(LocalTime.from(estToLocalZDT));
            estToLocalZDT = estToLocalZDT.plusMinutes(10);
        }

        startComboBox.setItems(startTime);
        endComboBox.setItems(endTime);
    }

    /**
     * Adds new appointment to database. This method performs a series of checks to ensure that valid data is added to the database and no business rules are broken. If all checks are passed, the new appointment is added to the database.
     *
     * @param event Is clicking submit button.
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
            alert.setHeaderText("Start and End times incorrect.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() ||
                titleTextField.getText().isEmpty() || startComboBox.getSelectionModel().isEmpty() || endComboBox.getSelectionModel().isEmpty()
                || typeComboBox.getSelectionModel().isEmpty() || userComboBox.getSelectionModel().isEmpty() || titleTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            alert.setHeaderText("Invalid data in form.");
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

                if (!overlapCheck(customer, appStartDT, appEndDT)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Customer already has an appointment at this time.");
                    alert.setHeaderText("Appointment overlap.");
                    Optional<ButtonType> result = alert.showAndWait();
                } else {
                    appointmentsDao.addAppointment(title, description, location, type, appStartTS, appEndTS, customer, user, contact);
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
     * Appointment types. This method sets the appointment types.
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
     * Checks if selected date is a weekend. This method checks if the selected date is a weekend and if it returns false, a new appointment is not allowed to be added to the database.
     *
     * @param localDate Date from date picker
     * @return boolean
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
     * Checks start and end times. This method checks the start and end times and ensure that they are in a valid order. A start time cannot be after an end time and an end time cannot be before a start time.
     *
     * @param start localTime from the startComboBox
     * @param end   endTime from the endComboBox
     * @return boolean
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
     * Generates a list of customer appointments. This method generates a list of appoints associated with a specific customer Id to be check for overlapping appointments.
     *
     * @param Id is the customer ID that needs to be checked
     * @return a filtered list of appointments for that customer.
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
     * Check for appointment conflicts. This method checks that the customer does not have any other appointments scheduled at the same time as the new one.
     *
     * @param Id    customer ID from the customerIdComboBox.
     * @param start LocalDateTime of the start of the appointment.
     * @param end   LocalDateTime of the end of the appointment.
     * @return boolean
     * @throws SQLException
     */
    @FXML
    boolean overlapCheck(int Id, LocalDateTime start, LocalDateTime end) throws SQLException {
        Id = customerIdComboBox.getValue().getCustomerId();
        ObservableList<Appointments> customerApps = customerAppointments(Id);

        for (Appointments a : customerApps) {
            if (start.isAfter(a.getStart()) && start.isBefore(a.getEnd()) ||
                    end.isAfter(a.getStart()) && end.isBefore(a.getEnd()) ||
                    start.isBefore(a.getStart()) && end.isAfter(a.getEnd()) ||
                    start.equals(a.getStart()) && end.equals(a.getEnd()) ||
                    start.equals(a.getStart()) || end.equals(a.getEnd())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cancels a new appointment. This method will cancel adding a new appointment to the database. All information is cleared.
     *
     * @param event is clicking the cancel button.
     * @throws IOException
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel adding a customer?");
        alert.setHeaderText("Cancel new appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initialize form. This method initializes the different combo boxes with options upon the form opening.
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
