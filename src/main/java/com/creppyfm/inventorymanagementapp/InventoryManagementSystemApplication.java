package com.creppyfm.inventorymanagementapp;

import com.creppyfm.inventorymanagementapp.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryManagementSystemApplication extends Application {
    public static Scene scene;
    public static Stage stage;

    /***
     * Starts application.
     *
     * @param stage -> view set to be displayed.
     * @throws IOException if location cannot be determined.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(InventoryManagementSystemApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(root, 1920, 1080);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /*
    * JavaDoc files located at /InventoryManagementApp/src/main/java/JavaDoc/
    * */
    /***
     * Initializes sample data for both parts and products.
     * Add parts/products to corresponding Observable Lists to be used to display table views.
     *
     * FUTURE ENHANCEMENTS:
     *      -> link to database for persistent data.
     *      -> check/update table views using each character of search input.
     *      -> add 'enter' key press as keyAction for search features.
     *
     */
    public static void main(String[] args) {
        Inventory.addPart(new InHouse(1, "Crash Cymbal", 359.99, 15, 2, 25, 1100));
        Inventory.addPart(new InHouse(2, "Ride Cymbal", 479.99, 7, 1, 25, 1200));
        Inventory.addPart(new InHouse(3, "HiHat Pair", 459.99, 5, 1, 25, 1300));
        Inventory.addPart(new InHouse(4, "Sticks", 9.99, 50, 10, 100, 1400));

        Inventory.addPart(new Outsourced(11, "Throne", 179.99, 25, 1, 50, "Roc N Soc"));
        Inventory.addPart(new Outsourced(12, "Single Bass Pedal", 199.99, 10, 1, 25, "DW"));
        Inventory.addPart(new Outsourced(13, "Double Bass Pedal", 699.99, 5, 1, 25, "DW"));
        Inventory.addPart(new Outsourced(14, "Boom Cymbal Stand", 199.99, 25, 3, 50, "Tama"));
        Inventory.addPart(new Outsourced(15, "HiHat Stand", 179.99, 22, 1, 50, "Mapex"));
        Inventory.addPart(new Outsourced(16, "4-Drum Head Pack", 159.99, 5, 1, 10, "Remo"));
        Inventory.addPart(new Outsourced(17, "4-Drum Shell Pack", 799.99, 5, 1, 10, "Mapex"));
        Inventory.addPart(new Outsourced(18, "6-Drum Head Pack", 199.99, 7, 1, 10, "Remo"));
        Inventory.addPart(new Outsourced(19, "6-Drum Shell Pack", 1599.99, 7, 1, 10, "Tama"));
        Inventory.addPart(new Outsourced(20, "Drum Key", 4.99, 29, 10, 100, "Tama"));

        Inventory.addProduct(new Product(1, "Jazz Drum Set", 3999.99, 5, 1, 10));
        Inventory.addProduct(new Product(2, "Rock Drum Set", 5999.99, 7, 1, 10));
        Inventory.addProduct(new Product(3, "Metal Drum Set", 9999.99, 3, 1, 10));
        Inventory.addProduct(new Product(4, "Fusion Drum Set", 7999.99, 5, 1, 10));

        launch();
    }
}