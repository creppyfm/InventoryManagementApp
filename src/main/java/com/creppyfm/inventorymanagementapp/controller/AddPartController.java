package com.creppyfm.inventorymanagementapp.controller;

import com.creppyfm.inventorymanagementapp.InventoryManagementSystemApplication;
import com.creppyfm.inventorymanagementapp.model.InHouse;
import com.creppyfm.inventorymanagementapp.model.Inventory;
import com.creppyfm.inventorymanagementapp.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {

    @FXML
    private Label inHouseOrOutsourced;

    @FXML
    private ToggleGroup InHouseOrOutsourcedTG;

    @FXML
    private TextField addPartIDTxt;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private TextField addPartInventoryTxt;

    @FXML
    private TextField addPartMachineIDOrCompanyNameTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private TextField addPartPriceCostTxt;

    /***
     * Switches 'inHouseOrOutsourced' label to read as 'Machine ID'.
     *
     * @param event -> 'addPartInHouseSelected' button pressed
     *
     */
    @FXML
    void addPartInHouseSelected(ActionEvent event) {
        inHouseOrOutsourced.setText("Machine ID");
    }

    /***
     * Switches 'inHouseOrOutsourced' label to read as 'Company Name'.
     *
     * @param event -> 'addPartOutsourcedSelected' button pressed
     *
     */
    @FXML
    void addPartOutsourcedSelected(ActionEvent event) {
        inHouseOrOutsourced.setText("Company Name");
    }

    /***
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionCancelPart' button pressed
     * @throws IOException if location cannot be determined
     *
     */
    @FXML
    void onActionCancelPart(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Sets parameter variables as values collected from 'AddPart.fxml'.
     * Creates a new 'Part' using aforementioned parameter variables.
     * Calls 'Inventory.addPart' method to add new part to allParts list.
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionSavePart' button pressed.
     * @throws NumberFormatException if input fields contain inappropriate characters.
     * @throws IOException if location cannot be determined.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
    try {
        int id = (int) ((Math.random() * 1000) % 100); //Integer.parseInt(addPartIDTxt.getText());
        String name = addPartNameTxt.getText();
        double price = Double.parseDouble(addPartPriceCostTxt.getText());
        int stock = Integer.parseInt(addPartInventoryTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        int max = Integer.parseInt(addPartMaxTxt.getText());
        boolean inHouse = addPartInHouseRBtn.isSelected() ? true : false;

        if (max < min) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Maximum inventory cannot be less than minimum inventory.");
            errorMessage.showAndWait();
            return;
        } else if (stock < min || stock > max) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR, "Inventory must be between maximum and minimum inventory.");
            errorMessage.showAndWait();
            return;
        }

        if (inHouse) {
            int machineId = Integer.parseInt(addPartMachineIDOrCompanyNameTxt.getText());
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
        } else {
            String companyName = addPartMachineIDOrCompanyNameTxt.getText();
            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
        }

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

}
