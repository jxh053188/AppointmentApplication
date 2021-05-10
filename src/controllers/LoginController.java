package controllers;
import dao.UserDao;
import dao.UserDaoImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Users;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login to the application. This class allows the user to login to the applicaiton and generates a login log.
 */
public class LoginController implements Initializable {
    UserDao userDao = new UserDaoImpl();

    @FXML
    public Button submitButton;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField password;

    @FXML
    public Label loginLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label passwordLabel;

    @FXML
    public Label timeLabel;

    @FXML
    public Label countryLabel;

    @FXML
    public Label error;

    @FXML
    public Label zoneIdLabel;

    @FXML
    public Label timezoneLabel;

    ResourceBundle rb = ResourceBundle.getBundle("utils.Nat", Locale.getDefault());


    /**
     * Checks user credentials. This method checks the user credentials for login.
     *
     * @param userName is the username.
     * @param password is the user password.
     * @return boolean
     * @throws SQLException
     */
    private boolean login(String userName, String password) throws SQLException {
        ObservableList<Users> users = userDao.getAllUsers();
        boolean success = false;

        for (Users user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                success = true;
            } else {
                success = false;
            }
        }
        return success;
    }

    /**
     * Logs user into the application. This method runs the login validation check. If successful the main screen is loaded and a successful login is written to log. If unsuccessful the user is shown an error and an unsuccessful login is written to the log.
     *
     * @param event is clicking the login button.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onSubmit(ActionEvent event) throws IOException, SQLException {
        String filename = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter logFile = new PrintWriter(fileWriter);
        String log;

        if (login(usernameField.getText(), password.getText())) {
            log = "Login Successful: " + usernameField.getText() + " at " + LocalDateTime.now();
            logFile.println(log);
            logFile.close();
            System.out.println("File Written!");
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            MainScreenController controller = loader.getController();
            controller.upcomingAppointments(usernameField.getText());
        } else {
            log = "Login Unsuccessful: " + usernameField.getText() + " at " + LocalDateTime.now();
            logFile.println(log);
            logFile.close();
            System.out.println("File Written!");

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                error.setText(rb.getString("Error"));
            }
        }
    }


    /**
     * Initializes login form. Initializes the login form based on the user location. Will change the language based off of Locale.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("h:mm a"));
        timeLabel.setText(time);

        //Locale.setDefault(Locale.FRANCE);

        ResourceBundle rb = ResourceBundle.getBundle("utils.Nat", Locale.getDefault());
        timezoneLabel.setText(Locale.getDefault().getDisplayName());
        zoneIdLabel.setText(ZoneId.systemDefault().getId());


        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginLabel.setText(rb.getString("Login"));
            submitButton.setText(rb.getString("Submit"));
            countryLabel.setText(rb.getString("Country"));


        }


    }
}
