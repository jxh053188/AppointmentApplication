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
import models.Countries;
import models.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Create a new customer. This class allows a user to create a new customer in the database.
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivisions> stateComboBox;

    CustomersDao customersDao = new CustomersDaoImpl();
    FirstLevelDivisionsDao FLDDao = new FirstLevelDivisionsDaoImpl();
    CountriesDao countriesDao = new CountriesDaoImpl();

    /**
     * Closes create customer form. This method will close the create customer form without submitting any data to the database.
     *
     * @param event is clicking on the cancel button.
     * @throws IOException
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The form will be cleared and no data will be saved. \nAre you sure you wish to cancel adding a customer?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/views/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Filters first level division. This method will filter the first level division after a user selects a country.
     *
     * @param event is selecting a country from combo box.
     * @throws SQLException
     */
    @FXML
    void onSelectCountry(ActionEvent event) throws SQLException {
        Countries country = countryComboBox.getSelectionModel().getSelectedItem();

        ObservableList<FirstLevelDivisions> firstLevelDivisions = FLDDao.getAllFirstLevelDivisions();
        ObservableList<FirstLevelDivisions> states = FXCollections.observableArrayList();
        for (FirstLevelDivisions divisions : firstLevelDivisions)
            if (divisions.getCountryId() == country.getCountryID())
                states.add(divisions);

        stateComboBox.setItems(states);
    }

    /**
     * Sends data to the database. This method will perform several validation checks and if they all pass, will submit the data to the database.
     *
     * @param event is clicking the submit button.
     */
    @FXML
    void onSubmit(ActionEvent event) {
        if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                postalCodeTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() || stateComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            try {
                String name = customerNameTextField.getText();
                String address = addressTextField.getText();
                String postCode = postalCodeTextField.getText();
                String phone = phoneNumberTextField.getText();
                int state = stateComboBox.getValue().getDivisionId();

                customersDao.addCustomer(name, address, postCode, phone, state);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/views/CustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }

    }


    /**
     * Initializes the create customer form. This method will set the country combo box with data upon opening the form.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryComboBox.setItems(countriesDao.getAllCountries());
            //stateComboBox.setItems(FLDDao.getAllFirstLevelDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
