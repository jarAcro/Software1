package controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is the controller for the add product controller of the application.
 *
 * @author Chase Alan Jarvis
 */
public class AddProductController implements Initializable {

    /** Initialize Stage object. */
    Stage stage;

    /** Initialize Parent object. */
    Parent scene;

    /** Observable list of part Part called associated parts. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Product Inventory text field. */
    @FXML
    private TextField addProductInvTXT;

    /** Product Max text field. */
    @FXML
    private TextField addProductMaxTXT;

    /** Product Min text field. */
    @FXML
    private TextField addProductMinTXT;

    /** Product name text field. */
    @FXML
    private TextField addProductNameTXT;

    /** Product price text field. */
    @FXML
    private TextField addProductPriceTXT;

    /** Product search text field. */
    @FXML
    private TextField addProductSearchTXT;

    /** table view Part bottom table. */
    @FXML
    private TableView<Part> bottomAddProductTable;

    /** Inventory for table column in part table. */
    @FXML
    private TableColumn<Part, Integer> bottomTableInvLevelCOL;

    /** part ID column in part table. */
    @FXML
    private TableColumn<Part, Integer> bottomTablePartIDCOL;

    /** Part name column in  part table. */
    @FXML
    private TableColumn<Part, String> bottomTablePartName;

    /** Part price in part table. */
    @FXML
    private TableColumn<Part, Double> bottomTablePriceCOL;

    /** Top table in add product form. */
    @FXML
    private TableView<Part> topAddProductTable;

    /** Inventory column part table */
    @FXML
    private TableColumn<Part, Integer> topTableInvLevelCOL;

    /** Part ID column top table. */
    @FXML
    private TableColumn<Part, Integer> topTablePartIDCOL;

    /** Part name column */
    @FXML
    private TableColumn<Part, String> topTablePartNameCOL;

    /** Price column */
    @FXML
    private TableColumn<Part, Double> topTablePriceCOL;

    /**
     * When product is searched and found the list is filtered.
     * @param event Triggered when using search text field.
     */
    @FXML
    void onActionAddProductSearch(ActionEvent event) {
        String searchPart = addProductSearchTXT.getText();

        if (searchPart.isBlank()) {
            topAddProductTable.setItems(Inventory.getAllParts());

        } else {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchPart));
                if (part == null) {
                    throw new NumberFormatException(); //to force search by name
                } else {
                    topAddProductTable.getSelectionModel().select(part);
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> list = Inventory.lookUpPart(searchPart);
                if (list.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Part not found");
                    alert.setContentText("The item you are searching for does not meet any part");
                    alert.show();
                } else {
                    topAddProductTable.setItems(list);
                }
            }

        }
    }

    /**
     * Takes the selected item and moves them to the associated parts table.
     *
     * @param event Triggered when add button is clicked.
     */
    @FXML
    void onActionAddButtonClicked(ActionEvent event) {
        Part part = topAddProductTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part.");
            alert.show();
            return;
        }
        associatedParts.add(part);
    }

    /**
     * Removes the selected item, from the associated parts table.
     *
     * @param event Triggers when associated part button clicked.
     */
    @FXML
    void onActionRemoveAssociatedPartButtonClicked(ActionEvent event) {
        Part part = bottomAddProductTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part.");
            alert.show();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please Confirm");
            alert.setContentText("Are you sure you want to delete this Associated Part?");
            alert.showAndWait();
        }
        associatedParts.remove(part);
    }

    /**
     * When cancel button is clicked, the program returns to main form.
     *
     * @param event Triggers when cancel button clicked.
     * @throws IOException Required when using load method.
     */
    @FXML
    void onActionReturnMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * When save button is clicked the inputs to the txt fields are saved and stored in table view. The application then returns to main form.
     *
     * @param event Triggers when save button clicked.
     * @throws IOException Required when using load method.
     */
    @FXML
    void onActionAddProductSaveButton(ActionEvent event) throws IOException {
        try {

            String name = addProductNameTXT.getText();
            double price = Inventory.getPrice(addProductPriceTXT.getText());
            int stock = Inventory.getInv(addProductInvTXT.getText());
            int min = Inventory.getMin(addProductMinTXT.getText());
            int max = Inventory.getMax(addProductMaxTXT.getText());

            String message = Inventory.isDataValid(name,max,min, stock, price, "");

            if (!message.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(message);
                alert.show();
                return;
            }
            Product product = new Product(Inventory.getNextProductId(), name, price, stock, min, max);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {

            //creating of a popup dialog box/ webinar 18
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }
    }
    /**
     * This initialize method is loading information into the table by cell id.
     *
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topAddProductTable.setItems(Inventory.getAllParts());
        topTablePartIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        topTablePartNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        topTablePriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        topTableInvLevelCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bottomAddProductTable.setItems(associatedParts);
        bottomTablePartIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomTablePriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        bottomTableInvLevelCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


}