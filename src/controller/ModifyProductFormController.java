package controller;

import Model.*;
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
 * This class adds funtionality to the modify product form.
 *
 * @author Chase Alan Jarvis
 */
public class ModifyProductFormController implements Initializable {

    /** Initializes Parent object */
    Stage stage;
    /** Initializes Scene object. */
    Parent scene;

    /** selected product declaration */
    Product selectedProduct;

    /** Observable list of parts added to list called associated parts.  */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Bottom table of parts. */
    @FXML
    private TableView<Part> modProductBottomTable;

    /** Product ID column */
    @FXML
    private TableColumn<Part, Integer> modProductBottomTableIDCOL;

    /** Product inventory column */
    @FXML
    private TableColumn<Part, Integer> modProductBottomTableInvCOL;

    /** Product name column */
    @FXML
    private TableColumn<Part, String> modProductBottomTableNameCOL;

    /** Product price column */
    @FXML
    private TableColumn<Part, Double> modProductBottomTablePriceCOL;

    /** Product ID text field */
    @FXML
    private TextField ModProductIDTXT;

    /** Product Inventory text field */
    @FXML
    private TextField modProductInvTXT;

    /** Product max text field */
    @FXML
    private TextField modProductMaxTXT;

    /** Product min text field */
    @FXML
    private TextField modProductMinTXT;

    /** Product name text field */
    @FXML
    private TextField modProductNameTXT;

    /** Product Price text field */
    @FXML
    private TextField modProductPriceTXT;

    /** Product search text field */
    @FXML
    private TextField modProductSearchTXT;

    /** Top parts table */
    @FXML
    private TableView<Part> modProductTopTable;

    /** Product table ID column */
    @FXML
    private TableColumn<Part, Integer> modProductTopTableIDCOL;

    /** Product inventory column */
    @FXML
    private TableColumn<Part, Integer> modProductTopTableInvCOL;

    /** Product name column */
    @FXML
    private TableColumn<Part, String> modProductTopTableNameCOL;

    /** Price column */
    @FXML
    private TableColumn<Part, Double> modProductTopTablePriceCOL;

    /**
     * Highlights part if found by id. and filters the list when found by partial name.
     *
     * @param event Triggers when search text field is used.
     */
    @FXML
    void onActionSearchModProduct(ActionEvent event) {
        String searchPart = modProductSearchTXT.getText();

        if (searchPart.isBlank()) {
            modProductTopTable.setItems(Inventory.getAllParts());

        } else {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchPart));
                if (part == null) {
                    throw new NumberFormatException(); //to force search by name
                } else {
                    modProductTopTable.getSelectionModel().select(part);
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> list = Inventory.lookUpPart(searchPart);
                if (list.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not found");
                    alert.setContentText("The item you are searching for does not meet any part");
                    alert.show();
                } else {
                    modProductTopTable.setItems(list);
                }
            }


        }
    }
    /**
     * Adds selected part to associated parts table.
     * @param event Triggered when add button clicked.
     */
    @FXML
    void onActionModProductAddButtonClicked(ActionEvent event) {
        Part part = modProductTopTable.getSelectionModel().getSelectedItem();
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
     * Saves the modified part back to the product table.
     * @param event Triggers when save button clicked.
     * @throws IOException Required to use load method.
     */
    @FXML
    void onActionModProductSaveButtonClicked(ActionEvent event) throws IOException {
        int id = Integer.parseInt(ModProductIDTXT.getText());
        String name = modProductNameTXT.getText();
        double price = Inventory.getPrice(modProductPriceTXT.getText());
        int stock = Inventory.getInv(modProductInvTXT.getText());
        int min = Inventory.getMin(modProductMinTXT.getText());
        int max = Inventory.getMax(modProductMaxTXT.getText());
        int productIndex = Inventory.getAllProduct().indexOf(selectedProduct);

        String message = Inventory.isDataValid(name, max, min, stock, price, "");
        if (!message.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(message);
            alert.show();
            return;
        }

        Product product = new Product(id, name, price, stock, min, max);

        for (Part part : associatedParts) {
            product.addAssociatedPart(part);
        }
        Inventory.updateProduct(productIndex, product);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Removes selected part in the associated parts table.
     *
     * @param event Triggered when remove associated part button clicked.
     */
    @FXML
    void onActionRemoveAssociatedPartButtonClicked(ActionEvent event) {
        Part part = modProductBottomTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part.");
            alert.show();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please confirm");
            alert.setContentText("Are you sure you want to delete this Associated Part?");
            alert.showAndWait();
        }
        associatedParts.remove(part);
    }
    /**
     * Returns to main form when cancel button clicked.
     *
     * @param event Triggered when cancel button clicked.
     * @throws IOException Required to use the load method.
     */
    @FXML
    void onActionReturnMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Takes a part from selected part and sends it to the table view in modify product.
     *
     * @param product Triggered when modify product is being used.
     */
    public void sendProduct(Product product) {
        selectedProduct = product;
        ModProductIDTXT.setText(String.valueOf(selectedProduct.getId()));
        modProductNameTXT.setText(selectedProduct.getName());
        modProductInvTXT.setText(String.valueOf(selectedProduct.getStock()));
        modProductPriceTXT.setText(String.valueOf(selectedProduct.getPrice()));
        modProductMaxTXT.setText(String.valueOf(selectedProduct.getMax()));
        modProductMinTXT.setText(String.valueOf(selectedProduct.getMin()));
        for (Part part : selectedProduct.getAllAssociatedParts()) {
            associatedParts.add(part);
        }
    }
    /**
     * This initialize method fills in the modify product table.
     *
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modProductTopTable.setItems(Inventory.getAllParts());
        modProductTopTableIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductTopTableNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductTopTablePriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProductTopTableInvCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

        modProductBottomTable.setItems(associatedParts);
        modProductBottomTableIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductBottomTableNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductBottomTablePriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProductBottomTableInvCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}