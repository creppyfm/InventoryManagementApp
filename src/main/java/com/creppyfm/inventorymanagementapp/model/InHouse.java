package com.creppyfm.inventorymanagementapp.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InHouse extends Part{

    private int machineID;

    public InHouse (int id,
                    String name,
                    double price,
                    int stock,
                    int min,
                    int max,
                    int machineID) {
        super(id,
            name,
            price,
            stock,
            min,
            max);

        this.machineID = machineID;
    }

    /***
     * Sets value of 'machineID' equal to that of parameter 'machineID'.
     *
     * @param machineID -> integer value 'machineID'.
     */
    public void setMachineID (int machineID) {
        this.machineID = machineID;
    }

    /***
     * Returns value of 'machineID'.
     *
     */
    public int getMachineID () {
        return machineID;
    }
}
