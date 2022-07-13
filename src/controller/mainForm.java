package controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the main form. It provides the tables and buttons for interacting with data.
 *
 * @author Chase Alan Jarvis
 */
public class mainForm implements Initializable {

    /** Initialize Stage object. */
    Stage stage;
    /** Initialize Parent object. */
    Parent scene;
    //all Parts table
    //Here change the ? to the type of data inside the table, first ? represents object type, in this case the Part. Second ? specifies data type
    /** Part Table */
    @FXML
    private TableView<Part> partTablemainForm;

    /** Part ID column */
    @FXML
    private TableColumn<Part, Integer> partIDCOL;

    /** part name column */
    @FXML
    private TableColumn<Part, String> partNameCOL;

    /** Part inventory column */
    @FXML
    private TableColumn<Part, Integer> partInvCOL;

    /** Part price column */
    @FXML
    private TableColumn<Part, Double> partPriceCOL;

    /** Part search text field. */
    @FXML
    private TextField partSearchTXT;

    //all Product
    /** Product ID column */
    @FXML
    private TableColumn<Product, Integer> productIDCOL;

    /** Product inventory column */
    @FXML
    private TableColumn<Product, Integer> productInvCOL;

    /** Product name column */
    @FXML
    private TableColumn<Product, String> productNameCOL;

    /** Product price column */
    @FXML
    private TableColumn<Product, Double> productPriceCOL;

    /** Product search text field */
    @FXML
    private TextField productSearchTXT;

    /** Product table */
    @FXML
    private TableView<Product> productTablemainForm;

    /**
     * Closes the application.
     * @param event Triggers when exit button clicked.
     * @throws IOException Required to use load method.
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        System.exit(0);

    }
//All Parts
// This creates a stage to switch screens, simply copy code and change location of the view don't forget to add Parent and Scene variables
    /**
     * When add button is clicked it opens the add parts form.
     *
     * @param event Triggered when add button clicked.
     * @throws IOException Required when using load method.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes the selected part form the table view.
     *
     * @param event Triggered when delete button clicked.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part part = partTablemainForm.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing was deleted, please select a part");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part selected, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(part);
        }
    }
    /**
     * Takes data from the parts table view and sends them to the modify parts form.
     *
     * @param event Triggered when modify button clicked.
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartsForm.fxml"));
            loader.load();

            ModifyPartsFormController MPController = loader.getController();
            MPController.sendPart(partTablemainForm.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Nothing was selected");
        }
    }
    /**
     * Takes the selected part and filters the list, if noting matches then the list repopulates.
     *
     * @param event Triggered when using the search text field.
     * @throws IOException Required to use load method.
     */
    @FXML
    void onActionSearchPart(ActionEvent event) throws IOException {
        String searchPart = partSearchTXT.getText();

        if (searchPart.isBlank()) {
            partTablemainForm.setItems(Inventory.getAllParts());

        } else {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchPart));
                if (part == null) {
                    throw new NumberFormatException(); //to force search by name
                } else {
                    partTablemainForm.getSelectionModel().select(part);
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
                    partTablemainForm.setItems(list);
                }
            }


        }
    }

    //All Product
    /**
     * Takes data from the product table view and sends them to the modify product form.
     * @param event Triggered when modify button clicked.
     * @throws IOException Required when using load method.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
        loader.load();

        ModifyProductFormController MPController = loader.getController();
        MPController.sendProduct(productTablemainForm.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Takes you to the add product form.
     * @param event Triggered when add button clicked.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes selected item.
     * @param event Triggered when delete button clicked.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product product = productTablemainForm.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product");
            alert.show();
            return;
        }
        if (product.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("This product has an associated part and cannot be deleted.");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the product selected, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK ) {
            Inventory.deleteProduct(product);
        }
    }
    /**
     * Searches for id and partial name match and either highlights if found by ID or filters if found by partial name.
     *
     * @param event Triggered when Product search text field used.
     */
    @FXML
    void onActionProductSearch(ActionEvent event) {
        String searchProduct = productSearchTXT.getText();

        if (searchProduct.isBlank()) {
            productTablemainForm.setItems(Inventory.getAllProduct());

        } else {
            try {
                Product product = Inventory.lookUpProduct(Integer.parseInt(searchProduct));
                if (product == null) {
                    throw new NumberFormatException(); //to force search by name
                } else {
                    productTablemainForm.getSelectionModel().select(product);
                }
            } catch (NumberFormatException e) {
                ObservableList<Product> list = Inventory.lookUpProduct(searchProduct);
                if (list.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not found");
                    alert.setContentText("The item you are searching for does not meet any product");
                    alert.show();
                } else {
                    productTablemainForm.setItems(list);
                }
            }
        }
    }
    /**
     * Everytime this page is launched it will load an observable list of parts, this information is sent to main
     *
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //table setup
        partTablemainForm.setItems(Inventory.getAllParts());
        productTablemainForm.setItems(Inventory.getAllProduct());
//calls getid method
        partIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIDCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCOL.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvCOL.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


}