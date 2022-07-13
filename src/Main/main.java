package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the "main" function of this application. It is the starting point of the program. This class loads an applications for storing parts and products.
 * FUTURE ENHANCEMENT: A future enhancement I would recommend is to store the Parts and Products in a database so that the information is up-to-date and has some degree of redundency protection.
 *
 * @author Chase Alan Jarvis
 */
public class main extends Application {
    /**
     * start is the first thing called to load in the "stage".
     * @param primaryStage The starting of the application UI.
     * @throws Exception Required to use the load method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource("/view/mainForm.fxml")));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This is the main method.
     * @param args Beginning of the code execution.
     */
    public static void main(String[] args) {


        launch(args);
    }


}