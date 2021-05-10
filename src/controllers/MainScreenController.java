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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointments;
import models.Contacts;
import models.Users;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls main screens and appointments overview. This class sets and controls the main screen of the application included appointments overview.
 */
public class MainScreenController implements Initializable {


    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Appointments> appointmentsTable;

    @FXML
    private TableColumn<Appointments, Integer> appIdColumn;

    @FXML
    private TableColumn<Appointments, String> appTitleColumn;

    @FXML
    private TableColumn<Appointments, String> appDescriptionColumn;

    @FXML
    private TableColumn<Appointments, String> appLocationColumn;

    @FXML
    private TableColumn<Appointments, Integer> appContactColumn;

    @FXML
    private TableColumn<Appointments, String> appTypeColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndColumn;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdColumn;

    @FXML
    private TableColumn<Users, Integer> appUserIdColumn;

    @FXML
    private RadioButton monthViewRadio;

    @FXML
    private ToggleGroup viewChoice;

    @FXML
    private RadioButton weeklyViewRadio;

    @FXML
    private RadioButton viewAllRadio;

    AppointmentsDao appointmentsDao = new AppointmentsDaoImpl();
    UserDao userDao = new UserDaoImpl();
    ContactDao contactDao = new ContactDaoImpl();
    Contacts contacts;

    /**
     * Loads customer main screen. This method takes the user to the customer main screen.
     *
     * @param event clicking the customer button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onCustomerButton(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads add appointment screen. This method loads the add appointment screen for the user.
     *
     * @param event clicking the add appointment button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onAddAppointment(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddAppointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes selected appointment. This method will delete the selected appointment from the database.
     *
     * @param event clicking the delete button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onAppDelete(ActionEvent event) throws IOException, SQLException {
        Appointments selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ("Are you sure you want to delete this appoint?\nAppointment ID: " + selected.getAppointmentId() + "\nType: " + selected.getType()));
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Delete Appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                appointmentsDao.deleteAppointment(selected.getAppointmentId());
            }
        }
        appointmentsTable.setItems(appointmentsDao.getAllAppointments());
    }

    /**
     * Filters appointment view. This method will filter the appointment table between a weekly view, a monthly view, and the default view based off of user choice.
     *
     * @param event is choosing a radial button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onFilterView(ActionEvent event) throws IOException, SQLException {
        ObservableList<Appointments> filterView = FXCollections.observableArrayList();
        filterView.clear();

        if (weeklyViewRadio.isSelected()) {
            for (Appointments appointments : appointmentsDao.getAllAppointments()) {
                if (appointments.getStart().isAfter(LocalDateTime.now()) && appointments.getStart().isBefore(LocalDateTime.now().plusDays(7))) {
                    filterView.add(appointments);
                }
            }
            appointmentsTable.setItems(filterView);
        }
        if (monthViewRadio.isSelected()) {
            for (Appointments appointments : appointmentsDao.getAllAppointments()) {
                if (appointments.getStart().isAfter(LocalDateTime.now()) && appointments.getStart().isBefore(LocalDateTime.now().plusDays(30))) {
                    filterView.add(appointments);
                }
            }
            appointmentsTable.setItems(filterView);
        }
        if (viewAllRadio.isSelected()) {
            for (Appointments appointments : appointmentsDao.getAllAppointments()) {
                if (appointments.getStart().isAfter(LocalDateTime.now()))
                    filterView.add(appointments);
            }
            appointmentsTable.setItems(filterView);
        }

    }


    /**
     * Alerts of upcoming appointments. This method will give an alert if the current user has an upcoming appointment within the next 15 minutes including the time start time of the appointment.
     *
     * @param username is the username from the login screen.
     * @throws SQLException
     */
    void upcomingAppointments(String username) throws SQLException {
        //usernameLabel.setText(username);
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        ObservableList<Users> allUsers = userDao.getAllUsers();
        Appointments upcoming = null;
        Users currentUser = null;
        for (Users users : allUsers) {
            if (users.getUserName().equals(username)) {
                currentUser = users;
            }
        }
        for(Appointments appointments : allAppointments){
            long timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), appointments.getStart());
            assert currentUser != null;
            if (appointments.getUserId() == currentUser.getUserId() && (timeDifference >= 0 && timeDifference <= 15)) {
                upcoming = appointments;
            }
        }

        if (upcoming != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, ("Appointment ID:  " + upcoming.getAppointmentId() + " " + upcoming.getTitle() + "\nTime: " + upcoming.getStart()));
            alert.setHeaderText("You have an appointment in the next 15 minutes.");
            alert.setTitle("Upcoming Appointment!");
            Optional<ButtonType> result = alert.showAndWait();
        }
        if (upcoming == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, (currentUser.getUserName() + " , you have no appointments in the next 15 minutes."));
            alert.setHeaderText("No appointments in the next 15 minutes. ");
            alert.setTitle("No Upcoming Appointment!");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * Opens update appointment form. This method will open the form to update the selected appointment.
     *
     * @param event is clicking update button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void updateAppointment(ActionEvent event) throws IOException, SQLException {
        if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment is selected. \nPlease select an appointment to edit.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/EditAppointment.fxml"));
            loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            Appointments selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
            EditAppointmentController controller = loader.getController();
            controller.selectedAppointment(selectedAppointment);
        }

    }

    /**
     * Initializes main screen. This method will filter through a list of all appointments and add future ones to the table view. Start and End times are reformatted into a more readable format.
     * A lambda will loop through the appointments list and assign them to a new list of upcoming appointments. I used a lambda here because as the database grows, calling an iterator to loop through the list will slow down performance.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            LocalDateTime now = LocalDateTime.now();
            ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
            ObservableList<Appointments> upcomingAppointment = FXCollections.observableArrayList();

            allAppointments.forEach(a -> {
                if (a.getEnd().isAfter(now)) {
                    upcomingAppointment.add(a);
                }
            });

            appointmentsTable.setItems(upcomingAppointment);
            appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            appLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            appTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            appContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy h:mm");
            appStartColumn.setCellFactory(column -> {
                return new TableCell<Appointments, LocalDateTime>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText("");
                            setStyle("");
                        } else {
                            setText(dtf.format(item));
                        }
                    }
                };
            });

            appEndColumn.setCellFactory(column -> {
                return new TableCell<Appointments, LocalDateTime>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText("");
                            setStyle("");
                        } else {
                            setText(dtf.format(item));
                        }
                    }
                };
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Open reports screen. This method opens the report screen for the user.
     *
     * @param event is clicking the report button.
     * @throws IOException
     */
    @FXML
    void onReportsButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReportsView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method exits. This method exits the applications and disconnects from the database.
     *
     * @param event clicking exit button.
     */
    @FXML
    void onExit(ActionEvent event) {
        DBConnection.closeConnection();
        Runtime.getRuntime().exit(0);
    }
}
