package com.creppyfm.inventorymanagementapp.controller;

import com.creppyfm.inventorymanagementapp.InventoryManagementSystemApplication;
import com.creppyfm.inventorymanagementapp.model.InHouse;
import com.creppyfm.inventorymanagementapp.model.Inventory;
import com.creppyfm.inventorymanagementapp.model.Outsourced;
import com.creppyfm.inventorymanagementapp.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {

    @FXML
    private Label inHouseOrOutsourced;

    @FXML
    private ToggleGroup InHouseOrOutsourcedTG;

    @FXML
    private TextField modPartIDTxt;

    @FXML
    private RadioButton modPartInHouseRBtn;

    @FXML
    private TextField modPartInventoryTxt;

    @FXML
    private TextField modPartMachineIDOrCompanyNameTxt;

    @FXML
    private TextField modPartMaxTxt;

    @FXML
    private TextField modPartMinTxt;

    @FXML
    private TextField modPartNameTxt;

    @FXML
    private RadioButton modPartOutsourcedRBtn;

    @FXML
    private TextField modPartPriceCostTxt;

    /***
     * Switches 'inHouseOrOutsourced' label to read as 'Machine ID'.
     *
     * @param event -> 'modPartInHouseSelected' button pressed
     *
     */
    @FXML
    void modifyPartInHouseSelected(ActionEvent event) {
        inHouseOrOutsourced.setText("Machine ID");
    }

    /***
     * Switches 'inHouseOrOutsourced' label to read as 'Company Name'.
     *
     * @param event -> 'modPartOutsourcedSelected' button pressed
     *
     */
    @FXML
    void modifyPartOutsourcedSelected(ActionEvent event) {
        inHouseOrOutsourced.setText("Company Name");
    }

    /***
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionCancelPart' button pressed.
     * @throws IOException if location cannot be determined.
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
     * Pre-fills all applicable text fields with corresponding data from 'selectedPart'.
     *
     * @param selectedPart -> part that will serve to pre-fill text fields on 'ModifyPart.fxml'.
     *
     */
    public void setSelectedPart(Part selectedPart) {
        boolean inHouse = selectedPart instanceof InHouse ? true : false;
        if (inHouse) {
            InHouse selectedInHouse = (InHouse) selectedPart;
            inHouseOrOutsourced.setText("Machine ID");
            modPartMachineIDOrCompanyNameTxt.setText(String.valueOf(selectedInHouse.getMachineID()));
            modPartInHouseRBtn.setSelected(true);
        } else {
            Outsourced selectedOutsourced = (Outsourced) selectedPart;
            inHouseOrOutsourced.setText("Company Name");
            modPartMachineIDOrCompanyNameTxt.setText(selectedOutsourced.getCompanyName());
            modPartOutsourcedRBtn.setSelected(true);
        }
        modPartIDTxt.setText(String.valueOf(selectedPart.getId()));
        modPartNameTxt.setText(selectedPart.getName());
        modPartPriceCostTxt.setText(String.valueOf(selectedPart.getPrice()));
        modPartInventoryTxt.setText(String.valueOf(selectedPart.getStock()));
        modPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
        modPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));

    }

    /***
     * Sets parameter variables as values collected from 'ModifyPart.fxml'.
     * Creates a new 'Part' using aforementioned parameter variables.
     * Calls 'Inventory.updatePart' method to delete old part from, and add new part to, allParts list.
     * Switches view to 'Main.fxml'.
     *
     * @param event -> 'onActionSavePart' button pressed.
     * @throws NumberFormatException if input fields contain inappropriate characters.
     * @throws IOException if location cannot be determined.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modPartIDTxt.getText());
            String name = modPartNameTxt.getText();
            double price = Double.parseDouble(modPartPriceCostTxt.getText());
            int stock = Integer.parseInt(modPartInventoryTxt.getText());
            int min = Integer.parseInt(modPartMinTxt.getText());
            int max = Integer.parseInt(modPartMaxTxt.getText());
            boolean inHouse = modPartInHouseRBtn.isSelected() ? true : false;

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
                int machineID = Integer.parseInt(modPartMachineIDOrCompanyNameTxt.getText());
                InHouse modifiedInHouse = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(modifiedInHouse);
            } else {
                String companyName = modPartMachineIDOrCompanyNameTxt.getText();
                Outsourced modifiedOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(modifiedOutsourced);
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
