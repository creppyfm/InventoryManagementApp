package com.creppyfm.inventorymanagementapp.controller;

import com.creppyfm.inventorymanagementapp.InventoryManagementSystemApplication;
import com.creppyfm.inventorymanagementapp.model.Inventory;
import com.creppyfm.inventorymanagementapp.model.Part;
import com.creppyfm.inventorymanagementapp.model.Product;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private TableColumn<?, ?> modProductIDColBottom;

    @FXML
    private TableColumn<?, ?> modProductIDColTop;

    @FXML
    private TextField modProductIDTxt;

    @FXML
    private TableColumn<?, ?> modProductInventoryColBottom;

    @FXML
    private TableColumn<?, ?> modProductInventoryColTop;

    @FXML
    private TextField modProductInventoryTxt;

    @FXML
    private TextField modProductMaxTxt;

    @FXML
    private TextField modProductMinTxt;

    @FXML
    private TableColumn<?, ?> modProductNameColBottom;

    @FXML
    private TableColumn<?, ?> modProductNameColTop;

    @FXML
    private TextField modProductNameTxt;

    @FXML
    private TableColumn<?, ?> modProductPriceCostColBottom;

    @FXML
    private TableColumn<Part, Double> modProductPriceCostColTop;

    @FXML
    private TextField modProductPriceCostTxt;

    @FXML
    private TextField modProductSearchTxt;

    @FXML
    private TableView<Part> modProductTblViewBottom;

    @FXML
    private TableView<Part> modProductTblViewTop;

    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /***
     * Adds selected part to product's associated parts list.
     * Alerts user with warning if no part is selected.
     *
     * @param event -> 'onActionAddProductAddPart' button pressed.
     *
     */
    @FXML
    void onActionModProductAddPart(ActionEvent event) {
        Part part = modProductTblViewTop.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Part Selected");
            errorMessage.setContentText("Please select the part you wish to add.");
            errorMessage.showAndWait();
        }
        if (!associatedPartList.contains(part)) {
            associatedPartList.add(part);
        }
    }

    /***
     * Removes a selected part from product's associated parts list.
     * Confirms removal of part from list with confirmation prompt.
     * Alerts user with warning if no part is selected.
     *
     * @param event -> 'onActionRemoveAssociatedPartFromProduct' button pressed
     *
     */
    @FXML
    void onActionRemoveAssocPartFromProduct(ActionEvent event) {
        Part part = modProductTblViewBottom.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Part Selected");
            errorMessage.setContentText("Please select the part you wish to remove.");
            errorMessage.showAndWait();
        }
        if (associatedPartList.contains(part)) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Removal of Record");
            confirmation.setHeaderText("Confirm Removal of Record");
            confirmation.setContentText("Are you sure you want to remove " + part.getName() + " from associated parts?");
            Optional<ButtonType> selection = confirmation.showAndWait();
            if (selection.isPresent() && selection.get() == ButtonType.OK) {
                associatedPartList.remove(part);
            }
        }
    }

    /***
     * Sets parameter variables as values collected from 'ModifyProduct.fxml'.
     * Creates a new 'Product' using aforementioned parameter variables.
     * Calls 'Inventory.updateProduct' method to delete old product from, and add new product to allProducts list.
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionSaveProduct' button pressed.
     * @throws NumberFormatException if input fields contain inappropriate characters.
     * @throws IOException if location cannot be determined.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modProductIDTxt.getText());
            String name = modProductNameTxt.getText();
            double price = Double.parseDouble(modProductPriceCostTxt.getText());
            int stock = Integer.parseInt(modProductInventoryTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());

            if (max < min) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Maximum inventory cannot be less than minimum inventory.");
                errorMessage.showAndWait();
                return;
            } else if (stock < min || stock > max) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Inventory must be between maximum and minimum inventory.");
                errorMessage.showAndWait();
                return;
            }

            Product modifiedProduct = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(modifiedProduct);
            associatedPartList.forEach(part -> {
                if (part != associatedPartList) {
                    modifiedProduct.addAssociatedPart(part);
                }
            });

            Parent parent = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("Main.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();


        } catch (NumberFormatException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: Invalid Input");
            errorMessage.setContentText("Please ensure input fields contain valid characters.");
            errorMessage.showAndWait();
        }
    }

    /***
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionCancelProduct' button pressed.
     * @throws IOException if location cannot be determined.
     */
    @FXML
    void onActionCancelProduct(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }

    /***
     * Pre-fills all applicable text fields with corresponding data from 'selectedProduct'.
     *
     * @param selectedProduct -> product that will serve to pre-fill text fields on 'ModifyProduct.fxml'.
     *
     */
    public void setSelectedProduct(Product selectedProduct) {
        modProductIDTxt.setText(String.valueOf(selectedProduct.getId()));
        modProductNameTxt.setText(selectedProduct.getName());
        modProductPriceCostTxt.setText(String.valueOf(selectedProduct.getPrice()));
        modProductInventoryTxt.setText(String.valueOf(selectedProduct.getStock()));
        modProductMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        modProductMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        associatedPartList.addAll(selectedProduct.associatedPartsList);
    }

    /***
     * Searches 'modProductTblViewTop' for either matching ID or Name (partial or full.)
     * Method filters displayed list by input. If there is no matching part,
     * method displays information alert stating no matching parts could be found.
     * If search bar is empty, table view displays full list of parts.
     *
     * @param event -> 'onActionSearchParts' button pressed
     *
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        ObservableList<Part> partList = Inventory.getAllParts();
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String query = modProductSearchTxt.getText();
        for (Part part : partList) {
            if (String.valueOf(part.getId()).contains(query) ||
                    part.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(part);
            }
        }
        modProductTblViewTop.setItems(searchResults);
        if (searchResults.size() < 1) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("No Results Found");
            errorMessage.setContentText("No results matching " + query + ".");
            errorMessage.showAndWait();
        }
    }

    /***
     * Resets modProductTblViewTop to display all parts if search bar is empty.
     *
     * @param event -> 'modProductSearchTxt' is empty
     *
     */
    @FXML
    void modProductTblViewTopReset(KeyEvent event) {
        if (modProductSearchTxt.getText().isEmpty()) {
            modProductTblViewTop.setItems(Inventory.getAllParts());
        }
    }

    /***
     * Initializes table views for 'ModifyProduct.fxml'.
     *
     * @param url -> an absolute URL to initialize 'ModifyProduct.fxml'
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProductTblViewTop.setItems(Inventory.getAllParts());
        modProductIDColTop.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductNameColTop.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductInventoryColTop.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductPriceCostColTop.setCellValueFactory(new PropertyValueFactory<>("price"));

        //***REMEMBER TO CHANGE "Inventory.getAllParts()" TO "associatedPartsList"***
        modProductTblViewBottom.setItems(associatedPartList);
        modProductIDColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductInventoryColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductPriceCostColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}
