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
import models.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Allows user to generate reports. This class allows a user to generate reports. To speed up loading time, the user must either click the generate button, or choose a customer or contact to generate a report.
 */
public class ReportsController implements Initializable {
    @FXML
    private Label totalSales;

    @FXML
    private Label totalMarketing;

    @FXML
    private Label customerTotal;

    @FXML
    private Label statusTotal;

    @FXML
    private Label otherTotal;

    @FXML
    private Label janTotal;

    @FXML
    private Label febTotal;

    @FXML
    private Label marTotal;

    @FXML
    private Label aprilTotal;

    @FXML
    private Label mayTotal;

    @FXML
    private Label juneTotal;

    @FXML
    private Label julyTotal;

    @FXML
    private Label augTotal;

    @FXML
    private Label septTotal;

    @FXML
    private Label octTotal;

    @FXML
    private Label novTotal;

    @FXML
    private Label decTotal;

    @FXML
    private Label customerSalesTotal;

    @FXML
    private Label cusMarkTotal;

    @FXML
    private Label customerCSTotal;

    @FXML
    private Label customerStatusTotal;

    @FXML
    private Label customerOtherTotal;

    CustomersDao customersDao = new CustomersDaoImpl();
    ContactDao contactDao = new ContactDaoImpl();

    @FXML
    private TableView<Appointments> contactScheduleTable;

    @FXML
    private TableColumn<Appointments, Integer> appIdColumn;

    @FXML
    private TableColumn<Appointments, String> appTitleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointments, Integer> cusIdCol;
    AppointmentsDao appointmentsDao = new AppointmentsDaoImpl();
    @FXML
    private ComboBox<Customers> customerCombo;
    @FXML
    private ComboBox<Contacts> contactCombox;
    @FXML
    private ComboBox<String> monthComboBox;

    /**
     * Loads main screen. This method returns the user to the main appointments screen.
     *
     * @param event clicking main button.
     * @throws IOException
     */
    @FXML
    void onMainButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads customer screen. This method returns the user to the main customers screen.
     *
     * @param event clicking customers button.
     * @throws IOException
     */
    @FXML
    void onCustomersButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Generates total type by month report. This method generates the total number of customer appointments by type.
     *
     * @param event clicking generate button.
     * @throws SQLException
     */
    @FXML
    void onGenerateType(ActionEvent event) throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();
        for (Appointments appointments : allAppointments) {
            String date = appointments.getStart().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            //System.out.println(date);
            String monthChoice = monthComboBox.getValue();
            //System.out.println(monthChoice);
            if (date.contains(monthChoice)) {
                monthlyAppointments.add(appointments);
            }
            //System.out.println(monthlyAppointments);
        }

        int marketing = 0;
        int customer = 0;
        int sales = 0;
        int other = 0;
        int status = 0;

        for (Appointments a : monthlyAppointments) {
            switch (a.getType()) {
                case "Marketing":
                    marketing = marketing + 1;
                    break;
                case "Customer Service":
                    customer = customer + 1;
                    break;
                case "Sales":
                    sales = sales + 1;
                    break;
                case "Status Update":
                    status = status + 1;
                    break;
                default:
                    other = other + 1;
                    break;
            }
        }
        totalSales.setText(String.valueOf(sales));
        totalMarketing.setText(String.valueOf(marketing));
        customerTotal.setText(String.valueOf(customer));
        statusTotal.setText(String.valueOf(status));
        otherTotal.setText(String.valueOf(other));

    }

    /**
     * Generates total appointments by month. This method will generate the total number of appointments by month.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onGenerateMonth(ActionEvent event) throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        for (Appointments a : allAppointments) {
            String date = a.getStart().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            if (date.contains("January")) {
                jan = jan + 1;
            } else if (date.contains("February")) {
                feb = feb + 1;
            } else if (date.contains("March")) {
                mar = mar + 1;
            } else if (date.contains("April")) {
                apr = apr + 1;
            } else if (date.contains("May")) {
                may = may + 1;
            } else if (date.contains("June")) {
                jun = jun + 1;
            } else if (date.contains("July")) {
                jul = jul + 1;
            } else if (date.contains("August")) {
                aug = aug + 1;
            } else if (date.contains("September")) {
                sep = sep + 1;
            } else if (date.contains("October")) {
                oct = oct + 1;
            } else if (date.contains("November")) {
                nov = nov + 1;
            } else {
                dec = dec + 1;
            }
        }
        janTotal.setText(String.valueOf(jan));
        febTotal.setText(String.valueOf(feb));
        marTotal.setText(String.valueOf(mar));
        aprilTotal.setText(String.valueOf(apr));
        mayTotal.setText(String.valueOf(may));
        juneTotal.setText(String.valueOf(jun));
        julyTotal.setText(String.valueOf(jul));
        augTotal.setText(String.valueOf(aug));
        septTotal.setText(String.valueOf(sep));
        octTotal.setText(String.valueOf(oct));
        novTotal.setText(String.valueOf(nov));
        decTotal.setText(String.valueOf(dec));

    }

    /**
     * Generates type of appointments by customer. This method generates a report that shows the total number of appointments by type for the choosen customer.
     *
     * @param event choosing a customer.
     * @throws SQLException
     */
    @FXML
    void customerAppointmentType(ActionEvent event) throws SQLException {
        Customers customers = customerCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        ObservableList<Appointments> customerAppointments = FXCollections.observableArrayList();
        for (Appointments appointments : allAppointments) {
            if (appointments.getCustomerId() == customers.getCustomerId())
                customerAppointments.add(appointments);
        }

        int marketing = 0;
        int customer = 0;
        int sales = 0;
        int other = 0;
        int status = 0;

        for (Appointments a : customerAppointments) {
            switch (a.getType()) {
                case "Marketing":
                    marketing = marketing + 1;
                    break;
                case "Customer Service":
                    customer = customer + 1;
                    break;
                case "Sales":
                    sales = sales + 1;
                    break;
                case "Status Update":
                    status = status + 1;
                    break;
                default:
                    other = other + 1;
                    break;
            }
        }
        customerSalesTotal.setText(String.valueOf(sales));
        cusMarkTotal.setText(String.valueOf(marketing));
        customerCSTotal.setText(String.valueOf(customer));
        customerStatusTotal.setText(String.valueOf(status));
        customerOtherTotal.setText(String.valueOf(other));


    }

    /**
     * Generates a contact's schedule. This method will generate a complete schedule for the chosen contact. The start and end columns are reformatted into a more readable format.
     *
     * @param event choosing contact.
     * @throws SQLException
     */
    @FXML
    void contactScheduleGenerate(ActionEvent event) throws SQLException {
        Contacts contacts = contactCombox.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> allAppointments = appointmentsDao.getAllAppointments();
        ObservableList<Appointments> contactSchedule = FXCollections.observableArrayList();

        allAppointments.forEach(a -> {
            if (a.getContactId() == contacts.getContactId()) {
                contactSchedule.add(a);
            }
        });

        contactScheduleTable.setItems(contactSchedule);
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy h:mm");
        startCol.setCellFactory(column -> {
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

        endCol.setCellFactory(column -> {
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


    }

    /**
     * Months options. Creates the list of months for the monthsComboBox
     */
    void months() {
        ObservableList<String> months = FXCollections.observableArrayList(
                "January",
                "February",
                "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"
        );
        monthComboBox.setItems(months);
    }

    /**
     * Initializes reports table. This method initializes the reports table and populates the combo boxes with choices.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCombo.setItems(customersDao.getAllCustomers());
            contactCombox.setItems(contactDao.getAllContacts());
            months();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }
}
