module com.creppyfm.inventorymanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.creppyfm.inventorymanagementapp to javafx.fxml;
    exports com.creppyfm.inventorymanagementapp;
    exports com.creppyfm.inventorymanagementapp.controller;
    opens com.creppyfm.inventorymanagementapp.controller to javafx.fxml;
    exports com.creppyfm.inventorymanagementapp.model to javafx.fxml;
    opens com.creppyfm.inventorymanagementapp.model;

}