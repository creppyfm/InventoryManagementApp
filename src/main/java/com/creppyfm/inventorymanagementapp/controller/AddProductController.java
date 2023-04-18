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
import java.security.Key;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private TableColumn<Product, Integer> addProductIDColBottom;

    @FXML
    private TableColumn<Part, Integer> addProductIDColTop;

    @FXML
    private TextField addProductIDTxt;

    @FXML
    private TableColumn<Product, Integer> addProductInventoryColBottom;

    @FXML
    private TableColumn<Part, Integer> addProductInventoryColTop;

    @FXML
    private TextField addProductInventoryTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TableColumn<Product, String> addProductNameColBottom;

    @FXML
    private TableColumn<Part, String> addProductNameColTop;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TableColumn<Product, Double> addProductPriceCostColBottom;

    @FXML
    private TableColumn<Part, Double> addProductPriceCostColTop;

    @FXML
    private TextField addProductPriceCostTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addProductTblViewBottom;

    @FXML
    private TableView<Part> addProductTblViewTop;

    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /***
     * Adds selected part to product's associated parts list.
     * Alerts user with warning if no part is selected.
     *
     * @param event -> 'onActionAddProductAddPart' button pressed.
     *
     */
    @FXML
    void onActionAddProductAddPart(ActionEvent event) {
        Part part = addProductTblViewTop.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Part Selected");
            errorMessage.setContentText("Please select the part you wish to delete.");
            errorMessage.showAndWait();
        }
        if (!associatedPartList.contains(part)) {
            associatedPartList.add(part);
        }
    }

    /***
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionCancelProduct' button pressed.
     * @throws IOException if location cannot be determined.
     *
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
     * Removes a selected part from product's associated parts list.
     * Confirms removal of part from list with confirmation prompt.
     * Alerts user with warning if no part is selected.
     *
     * @param event -> 'onActionRemoveAssociatedPartFromProduct' button pressed
     *
     */
    @FXML
    void onActionRemoveAssociatedPartFromProduct(ActionEvent event) {
        Part part = addProductTblViewBottom.getSelectionModel().getSelectedItem();
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
     * Searches 'addProductTblViewTop' for either matching ID or Name (partial or full.)
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
        String query = addProductSearchTxt.getText();
        for (Part part : partList) {
            if (String.valueOf(part.getId()).contains(query) ||
                    part.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(part);
            }
        }
        addProductTblViewTop.setItems(searchResults);
        if (searchResults.size() < 1) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("No Results Found");
            errorMessage.setContentText("No results matching " + query + ".");
            errorMessage.showAndWait();

        }
    }

    /***
     * Resets addProductTblViewTop to display all parts if search bar is empty.
     *
     * @param event -> 'addProductSearchTxt' is empty
     *
     */
    @FXML
    void addProductTblViewTopReset(KeyEvent event) {
        if (addProductSearchTxt.getText().isEmpty()) {
            addProductTblViewTop.setItems(Inventory.getAllParts());
        }
    }

    /***
     * Sets parameter variables as values collected from 'AddProduct.fxml'.
     * Creates a new 'Product' using aforementioned parameter variables.
     * Adds any associated parts selected from addProductTblViewTop to new product's
     * associated parts list.
     * Calls 'Inventory.addProduct' method to add new part to allProducts list.
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionSaveProduct' button pressed.
     * @throws NumberFormatException if input fields contain inappropriate characters.
     * @throws IOException if location cannot be determined.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = (int) ((Math.random() * 2000) % 1000);
            String name = addProductNameTxt.getText();
            double price = Double.parseDouble(addProductPriceCostTxt.getText());
            int stock = Integer.parseInt(addProductInventoryTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());

            if (max < min) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Maximum inventory cannot be less than minimum inventory.");
                errorMessage.showAndWait();
                return;
            } else if (stock < min || stock > max) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Inventory must be between maximum and minimum inventory.");
                errorMessage.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);
            associatedPartList.forEach(part -> {
                if (part != associatedPartList) {
                    newProduct.addAssociatedPart(part);
                }
            });

            Inventory.addProduct(newProduct);

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
     * Initializes table views for 'AddProduct.fxml'.
     *
     * @param url -> an absolute URL to initialize 'AddProduct.fxml'
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTblViewTop.setItems(Inventory.getAllParts());
        addProductIDColTop.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameColTop.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryColTop.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCostColTop.setCellValueFactory(new PropertyValueFactory<>("price"));

        //***REMEMBER TO CHANGE "Inventory.getAllParts()" TO "associatedPartsList"***
        addProductTblViewBottom.setItems(associatedPartList);
        addProductIDColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCostColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
