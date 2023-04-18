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


public class MainController implements Initializable {

    @FXML
    private TextField mainPartSearchTxt;
    @FXML
    private TableColumn<Part, Integer> mainPartTblIDCol;
    @FXML
    private TableColumn<Part, Integer> mainPartTblInventoryCol;
    @FXML
    private TableColumn<Part, String> mainPartTblNameCol;
    @FXML
    private TableColumn<Part, Double> mainPartTblPriceCostCol;
    @FXML
    private TableView<Part> mainPartTblView;
    @FXML
    private TextField mainProductSearchTxt;
    @FXML
    private TableColumn<Product, Integer> mainProductTblIDCol;
    @FXML
    private TableColumn<Product, Integer> mainProductTblInventoryCol;
    @FXML
    private TableColumn<Product, String> mainProductTblNameCol;
    @FXML
    private TableColumn<Product, Double> mainProductTblPriceCostCol;
    @FXML
    private TableView<Product> mainProductTblView;

    /***
     * Switches view to 'AddPart.fxml'.
     *
     * @param event -> 'onActionAddParts' button pressed
     * @throws IOException -> if location cannot be determined
     */
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Switches view to 'ModifyPart.fxml'. Method is only run when a part is selected.
     * Otherwise, a popup alerts the user to select a part to modify.
     *
     * @param event -> 'onActionModifyParts' button pressed
     * @throws IOException -> if location cannot be determined
     */
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        Part selectedPart = mainPartTblView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Part Selected");
            errorMessage.setContentText("Please select the part you wish to modify.");
            errorMessage.showAndWait();

        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(InventoryManagementSystemApplication.class.getResource("ModifyPart.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                ModifyPartController modifyPartController = fxmlLoader.getController();
                modifyPartController.setSelectedPart(selectedPart);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Modify Part");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * Switches view to 'AddProduct.fxml'.
     *
     * @param event -> 'onActionAddProducts' button pressed
     * @throws IOException -> if location cannot be determined
     */
    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Switches view to 'ModifyProduct.fxml'. Method is only run when a product is selected.
     * Otherwise, a popup alerts the user to select a product to modify.
     *
     * @param event -> 'onActionModifyProduct' button pressed
     * @throws IOException -> if location cannot be determined
     */
    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {
            Product selectedProduct = mainProductTblView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Error: No Product Selected");
                errorMessage.setContentText("Please select the product you wish to modify.");
                errorMessage.showAndWait();

            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(InventoryManagementSystemApplication.class.getResource("ModifyProduct.fxml"));
                    Parent parent = fxmlLoader.load();
                    Scene scene = new Scene(parent);
                    ModifyProductController modifyProductController = fxmlLoader.getController();
                    modifyProductController.setSelectedProduct(selectedProduct);
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.setTitle("Modify Product");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /***
     * Deletes selected part from 'mainPartTblView'. Method is only run when a part is selected.
     * Otherwise, a popup alerts the user to select a part to delete.
     *
     * @param event -> 'onActionDeletePart' button pressed
     * @throws NullPointerException -> if part is not selected
     */
    @FXML
    void onActionDeleteParts(ActionEvent event) {
        try {
            if (mainProductTblView.getSelectionModel().getSelectedItems() != null) {
                Part partToDelete = mainPartTblView.getSelectionModel().getSelectedItem();
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirm Deletion of Record");
                confirmation.setHeaderText("Confirm Deletion of Record");
                confirmation.setContentText("Are you sure you want to delete " + partToDelete.getName() + "?");
                Optional<ButtonType> selection = confirmation.showAndWait();
                if (selection.isPresent() && selection.get() == ButtonType.OK) {
                    Inventory.deletePart(partToDelete);
                }
            }
        } catch (NullPointerException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Part Selected");
            errorMessage.setContentText("Please select the part you wish to delete.");
            errorMessage.showAndWait();

        }
    }

    /***
     * Deletes selected product. Method is only run when a product is selected, and
     * the selected product's 'associatedPartsList' is empty.
     * Otherwise, a popup alerts the user to select a product to delete or to delete associated
     * products first.
     *
     * @param event -> 'onActionDeleteProduct' button pressed
     * @throws NullPointerException -> if no product is selected
     */
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        try {
            ObservableList<Part> associatedParts = mainProductTblView.getSelectionModel().getSelectedItem().associatedPartsList;
            if (associatedParts.size() != 0) {
                Alert clearAssocParts = new Alert(Alert.AlertType.ERROR);
                clearAssocParts.setTitle("Error: Associated Parts");
                clearAssocParts.setHeaderText("Associated Parts");
                clearAssocParts.setContentText("Please clear associated parts.");
                clearAssocParts.showAndWait();

            } else {
                Product productToDelete = mainProductTblView.getSelectionModel().getSelectedItem();
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirm Deletion of Record");
                confirmation.setHeaderText("Confirm Deletion of Record");
                confirmation.setContentText("Are you sure you want to delete " + productToDelete.getName() + "?");
                Optional<ButtonType> selection = confirmation.showAndWait();
                if (selection.isPresent() && selection.get() == ButtonType.OK) {
                    Inventory.deleteProduct(productToDelete);
                }

            }
        } catch (NullPointerException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Error: No Product Selected");
            errorMessage.setContentText("Please select the product you wish to delete.");
            errorMessage.showAndWait();

        }
    }

    /***
     * Searches 'mainPartTblView' for either matching ID or Name (partial or full.)
     * Method filters displayed list by input. If there is no matching part,
     * method displays information alert stating no matching parts could be found.
     * If search bar is empty, table view displays full list of parts.
     *
     * @param event -> 'onActionSearchPart' button pressed
     *
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {
        ObservableList<Part> partList = Inventory.getAllParts();
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        String query = mainPartSearchTxt.getText();
        for (Part part : partList) {
            if (String.valueOf(part.getId()).contains(query) ||
                part.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(part);
            }
        }
        mainPartTblView.setItems(searchResults);
        if (searchResults.size() < 1) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("No Results Found");
            errorMessage.setContentText("No results matching " + query + ".");
            errorMessage.showAndWait();
        }
    }

    /***
     * Searches 'mainProductTblView' for either matching ID or Name (partial or full.)
     * Method filters displayed list by input. If there is no matching product,
     * method displays information alert stating no matching parts could be found.
     * If search bar is empty, table view displays full list of products.
     *
     * @param event -> 'onActionSearchProduct' button pressed
     *
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        ObservableList<Product> productList = Inventory.getAllProducts();
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        String query = mainProductSearchTxt.getText();
        for (Product product : productList) {
            if (String.valueOf(product.getId()).contains(query) ||
                    product.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(product);
            }
        }
        mainProductTblView.setItems(searchResults);
        if (searchResults.size() < 1) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("No Results Found");
            errorMessage.setContentText("No results matching " + query + ".");
            errorMessage.showAndWait();
        }
    }

    /***
     * Resets mainPartTblView to display all parts if search bar is empty.
     *
     * @param event -> 'mainPartSearchTxt' is empty
     *
     */
    @FXML
    void mainPartTblViewReset(KeyEvent event) {
        if (mainPartSearchTxt.getText().isEmpty()) {
            mainPartTblView.setItems(Inventory.getAllParts());
        }
    }

    /***
     * Resets mainProductTblView to display all products if search bar is empty.
     *
     * @param event -> 'mainProductSearchTxt' is empty
     *
     */
    @FXML
    void mainProductTblViewReset(KeyEvent event) {
        if (mainProductSearchTxt.getText().isEmpty()) {
            mainProductTblView.setItems(Inventory.getAllProducts());
        }
    }

    /***
     * Initializes table views for 'Main.fxml'.
     *
     * @param url -> an absolute URL to initialize 'Main.fxml'
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPartTblView.setItems(Inventory.getAllParts());
        mainPartTblIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartTblNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartTblInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartTblPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductTblView.setItems(Inventory.getAllProducts());
        mainProductTblIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductTblNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductTblInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductTblPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /***
     * Closes the application.
     *
     * @param event -> 'onActionCloseApplication' button pressed
     */
    @FXML
    void onActionCloseApplication(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
