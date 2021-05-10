import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.SQLException;

public class Main extends Application {

    /**
     * Load login page. This start method loads the login page for the user.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Manages connections. The main method connects to the database and disconnects upon close.
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        // write your code here
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }


}
