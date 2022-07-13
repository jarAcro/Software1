package controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class add functionality to the buttons and fields in the modify parts form controller.
 *
 * @author Chase Alan Jarvis
 */
public class ModifyPartsFormController implements Initializable {

    /** Initialize stage object. */
    Stage stage;
    /** Initialize parent object. */
    Parent scene;

    /** selected Part declaration. */
    Part selectedPart;

    /** Initialization of the checker if outsourced radio button is selected. */
    private boolean isOutsourced;

    /** Part ID column */
    @FXML
    private TextField ModifyPartIDTXT;

    /** Part inventory text field */
    @FXML
    private TextField ModifyPartInvTXT;

    /** Part machine ID text field */
    @FXML
    private TextField ModifyPartMachineIDTXT;

    /** Part max text field */
    @FXML
    private TextField ModifyPartMaxTXT;

    /** Part min text field */
    @FXML
    private TextField ModifyPartMinTXT;

    /** Part name text field */
    @FXML
    private TextField ModifyPartNameTXT;

    /** Part price text field */
    @FXML
    private TextField ModifyPartPriceTXT;

    /** Outsourced radio button */
    @FXML
    private RadioButton OutsourcedRadioButtonMP;

    /** Machine ID text field */
    @FXML
    private Label ModPartMachineID;

    /** In-house radio button */
    @FXML
    private RadioButton inHouseRadioButtonMP;

    /** Checks whether outsourced radio button is selected. */
    @FXML
    void onInHouse(ActionEvent event) {
        isOutsourced = false;
        ModPartMachineID.setText("Machine ID");
    }

    /** Checks if outsourced radio button is selected. */
    @FXML
    void onOutsourced(ActionEvent event) {
        isOutsourced = true;
        ModPartMachineID.setText("Company Name");
    }

    /** Returns you to the main form when cancel button clicked. */
    @FXML
    void onActionReturnMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves modified part to the parts table in main form.
     *
     * @param event Triggered when save button clicked.
     * @throws IOException Required to use load method.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Integer.parseInt(ModifyPartIDTXT.getText());
        String name = ModifyPartNameTXT.getText();
        double price = Inventory.getPrice(ModifyPartPriceTXT.getText());
        int stock = Inventory.getInv(ModifyPartInvTXT.getText());
        int min = Inventory.getMin(ModifyPartMinTXT.getText());
        int max = Inventory.getMax(ModifyPartMaxTXT.getText());
        int partIndex = Inventory.getAllParts().indexOf(selectedPart);

        String message = Inventory.isDataValid(name, max, min, stock, price, "");
        if (!message.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(message);
            alert.show();
            return;
        }

        if (isOutsourced == false) {

            int machineId = Inventory.getMachineId(ModifyPartMachineIDTXT.getText());
            Part inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.updatePart(partIndex, inHousePart);

        } else {

            String companyName = ModifyPartMachineIDTXT.getText();
            Part outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.updatePart(partIndex, outsourcedPart);
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Takes a part from selected part and sends it to the table view in modify part.
     *
     * @param part Triggers when part is being modified.
     */
    public void sendPart(Part part) {
        selectedPart = part;
        ModifyPartIDTXT.setText(String.valueOf(selectedPart.getId()));
        ModifyPartNameTXT.setText(selectedPart.getName());
        ModifyPartInvTXT.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartPriceTXT.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartMaxTXT.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartMinTXT.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inHouseRadioButtonMP.setSelected(true);
            ModPartMachineID.setText("Machine ID");
            ModifyPartMachineIDTXT.setText(String.valueOf((((InHouse) selectedPart).getMachineid())));
        } else {
            OutsourcedRadioButtonMP.setSelected(true);
            ModPartMachineID.setText("Company Name");
            ModifyPartMachineIDTXT.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    /**
     * Initialize method loads and runs first before other code is executed.
     *
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
