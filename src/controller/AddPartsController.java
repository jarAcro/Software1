package controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the add parts form of the application.
 *
 * @author Chase Alan Jarvis
 */
public class AddPartsController implements Initializable {

    /** Initialize Stage object. */
    Stage stage;

    /** initialize parent object.*/
    Parent scene;

    /** In house radio button. */
    @FXML
    private RadioButton InHouseRadioButton;

    /** Part name text field */
    @FXML
    private TextField addPartNameTXT;

    /** Inventory text field */
    @FXML
    private TextField addPartInvTXT;

    /** Min text field */
    @FXML
    private TextField addPartMinTXT;

    /** Part Max text field */
    @FXML
    private TextField addPartMaxTXT;

    /** Machine ID text field */
    @FXML
    private TextField addPartMachineIDTXT;

    /** Price text field */
    @FXML
    private TextField addPartPriceTXT;

    /** machine ID label */
    @FXML
    private Label partMachineIDLabel;

//Event Handlers for parts form
    /**
     * When the cancel button clicked it returns to the main form screen.
     *
     * @param event Triggered when cancel button is clicked.
     */
    @FXML
    void onActionReturnMainForm(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //try catch blocks handle exceptions. catch = message for when exception is caught
    /**
     * When save button is clicked it saves the information in the txt fields and returns to main form.
     * LOGICAL ERROR: originally this code was set to if (message.isBlank()),
     * which resulted in the error message popping up everytime. To correct this I added a (not !) operator to correct the logical error.
     *
     * @param event Triggers when save button clicked.
     */
    @FXML
    void onActionPartSave(ActionEvent event) throws IOException {
        //take references from above, must be wrapped in correct cast ex) parseInt
        //all variable names taken from Part.java/ InHouse
        try {
            String name = addPartNameTXT.getText();
            double price = Inventory.getPrice((addPartPriceTXT.getText()));
            int stock = Inventory.getInv(addPartInvTXT.getText());
            int min = Inventory.getMin(addPartMinTXT.getText());
            int max = Inventory.getMax(addPartMaxTXT.getText());
            String message = Inventory.isDataValid(name, max, min, stock, price, "");

            if (!message.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(message);
                alert.show();
                return;
            }

            if (InHouseRadioButton.isSelected()) {
                int machineId = Inventory.getMachineId(addPartMachineIDTXT.getText());
                Inventory.addPart(new InHouse(Inventory.getNextPartId(), name, price, stock, min, max, machineId));

            } else {
                String companyName = Inventory.getCompanyName(addPartMachineIDTXT.getText());
                Inventory.addPart(new Outsourced(Inventory.getNextPartId(), name, price, stock, min, max, companyName));
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {

            //creating of a popup dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.show();
        }
    }

    //Event to change the text based on which radiobutton is selected
    /**
     * Sets text to different value when a radio button clicked.
     * @param actionEvent Event triggered by clicking in-house radio button.
     */
    public void onInHouse(ActionEvent actionEvent) {
        partMachineIDLabel.setText("Machine ID");

    }

    /**
     * Sets text to different value when a radio button clicked.
     * @param actionEvent Event triggered by clicking outsourced radio button.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        partMachineIDLabel.setText("Company Name");

    }

    /**
     * Code placed in the initialize method is loaded first and ran before the rest of the code.
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }
}



