package controllers;

import dao.AppointmentsDao;
import dao.AppointmentsDaoImpl;
import dao.CustomersDao;
import dao.CustomersDaoImpl;
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
import models.Customers;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main customer screen. This class allows the user to access the main customer screen and all of the functions it contains.
 */
public class CustomersController implements Initializable {
    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<Customers> customersTable;

    @FXML
    private TableColumn<Customers, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private TableColumn<Customers, String> customerAddressColumn;

    @FXML
    private TableColumn<Customers, String> customerPostalCodeColumn;

    @FXML
    private TableColumn<Customers, String> customerPhoneColumn;

    @FXML
    private TableColumn<Customers, Integer> divisionIdColumn;

    @FXML
    private TableColumn<Customers, String> divisionColumn;

    CustomersDao customersDao = new CustomersDaoImpl();
    AppointmentsDao appointmentsDao = new AppointmentsDaoImpl();

    /**
     * Open appointments screen. This method allows the users to switch back to the appointments main screen.
     *
     * @param event is clicking the appointments button.
     * @throws IOException
     */
    @FXML
    void onAppointmentsButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens add customer screen. This method will open the screen that allows a user to create a new customer in the database.
     *
     * @param event is clicking add customer button.
     * @throws IOException
     */
    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddCustomer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens update customer screen. This method will get the selected customer and open the update customer screen.
     *
     * @param event is clicking the update customer screen.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException, SQLException {
        if (customersTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer is selected. \nPlease select a customer to edit.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/EditCustomer.fxml"));
            loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            Customers selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            EditCustomerController controller = loader.getController();
            controller.selectedCustomer(selectedCustomer);

        }
    }

    /**
     * Deletes a customer from the database. This method will get the selected customer from the database and confirm the users wants to delete that customer. If the user confirms yes, the customer is deleted. All appointments associated with that customer are also deleted.
     * This method contains a lambda that loops through the list of appointments and matches the customer ID. For every match, that appointment is deleted. With a large collection of appointments, the lambda will be faster since it does not have to create an iterator.
     *
     * @param event is clicking delete button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onDeleteCustomer(ActionEvent event) throws IOException, SQLException {
        Customers selected = customersTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ("Are you sure you want to delete this customer?\n" + "Customer: " + selected.getCustomerName() + "\nAll appoints with this customer will also be deleted."));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            allAppointments.forEach(a -> {
                if (a.getCustomerId() == selected.getCustomerId()) {
                    try {
                        appointmentsDao.deleteAppointment(a.getAppointmentId());
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            });
            customersDao.deleteCustomer(selected.getCustomerId());
        }

        customersTable.setItems(customersDao.getAllCustomers());
    }

    /**
     * Opens the reports screen. This method opens the reports screen for the user.
     *
     * @param event is clicking the reports button.
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
     * Exit application. This method exits the application and disconnects from the database.
     *
     * @param event is clicking the exit button.
     */
    @FXML
    void onExit(ActionEvent event) {
        DBConnection.closeConnection();
        Runtime.getRuntime().exit(0);
    }


    /**
     * Initializes customer screen. This method initializes the customer table upon opening the customers screen.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customersTable.setItems(customersDao.getAllCustomers());
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }
}
