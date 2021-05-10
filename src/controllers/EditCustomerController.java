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
import models.Customers;
import models.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Edit customer in database. This class allows a user to edit customers in the database.
 */
public class EditCustomerController implements Initializable {

    CustomersDao customersDao = new CustomersDaoImpl();
    CountriesDao countriesDao = new CountriesDaoImpl();
    FirstLevelDivisionsDao firstLevelDivisionsDao = new FirstLevelDivisionsDaoImpl();
    Customers customer;

    @FXML
    private TextField customerIdTextField;

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

    /**
     * Finds First Level Division name. This method will find the first level division name based off of the division ID. It loops through the list of First Level divisions until the ID is found.
     *
     * @param divisionId division ID.
     * @return First Level Division Name.
     * @throws SQLException
     */
    FirstLevelDivisions nameFromId(int divisionId) throws SQLException {
        ObservableList<FirstLevelDivisions> divisions = firstLevelDivisionsDao.getAllFirstLevelDivisions();
        FirstLevelDivisions state = null;
        for (FirstLevelDivisions d : divisions) {
            if (divisionId == d.getDivisionId()) {
                state = d;
            }
        }
        return state;
    }

    /**
     * Finds the country name. This method finds the country name associated with the customer, based off of the countryID.
     *
     * @param countryId is the country ID.
     * @return country name.
     * @throws SQLException
     */
    Countries countryFromDivision(int countryId) throws SQLException {
        ObservableList<Countries> countries = countriesDao.getAllCountries();
        Countries customerCountry = null;
        for (Countries c : countries) {
            if (countryId == c.getCountryID()) {
                customerCountry = c;
            }
        }
        return customerCountry;
    }

    /**
     * Populates customer data. This method will populate the form with the customer data from the database.
     *
     * @param customer is selected customer from customers page.
     * @throws SQLException
     */
    @FXML
    void selectedCustomer(Customers customer) throws SQLException {
        this.customer = customer;
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));
        customerNameTextField.setText(customer.getCustomerName());
        phoneNumberTextField.setText(customer.getPhone());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());

        FirstLevelDivisions customerState = nameFromId(customer.getDivisionId());
        Countries customerCountry = countryFromDivision(customerState.getCountryId());
        countryComboBox.setValue(customerCountry);
        stateComboBox.setValue(customerState);

    }

    /**
     * Returns user to customer screen. This method will return the user to the customer screen without saving any changes made to the form.
     *
     * @param event clicking the cancel button.
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
     * Filters First Level Divisions. This method will filter first level divisions based on the country the user chooses. The filtered list is set to a combo box.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onSelectCountry(ActionEvent event) throws SQLException {
        Countries country = countryComboBox.getSelectionModel().getSelectedItem();
        ObservableList<FirstLevelDivisions> firstLevelDivisions = firstLevelDivisionsDao.getAllFirstLevelDivisions();
        ObservableList<FirstLevelDivisions> states = FXCollections.observableArrayList();
        for (FirstLevelDivisions divisions : firstLevelDivisions)
            if (divisions.getCountryId() == country.getCountryID())
                states.add(divisions);

        stateComboBox.setItems(states);
    }

    /**
     * Saves changes to the database. This method will perform a series of validation checks, and submit the data to the database if everything is correct.
     *
     * @param event
     */
    @FXML
    void onSubmit(ActionEvent event) {
        if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                postalCodeTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There are blank fields or invalid data in the form. \nPlease check data entered.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            try {
                int id = Integer.parseInt(customerIdTextField.getText());
                String name = customerNameTextField.getText();
                String address = addressTextField.getText();
                String postCode = postalCodeTextField.getText();
                String phone = phoneNumberTextField.getText();
                int state = stateComboBox.getValue().getDivisionId();

                customersDao.updateCustomer(id, name, address, postCode, phone, state);
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
     * Initializes form. This method initializes the form and populates the combo boxes.
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
