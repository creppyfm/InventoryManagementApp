package com.creppyfm.inventorymanagementapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    public ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id,
                String name,
                double price,
                int stock,
                int min,
                int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /***
     * Adds selected part to product's 'associatedPartsList'.
     *
     * @param part -> part to be added to 'associatedPartsList'.
     */
    public void addAssociatedPart (Part part) {
        associatedPartsList.add(part);
    }

    /***
     * Sets product's id equal to that of 'id'.
     *
     * @param id -> int value to be set as 'id'.
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Sets product's name equal to that of 'name'.
     *
     * @param name -> string value to be set as 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Sets product's price equal to that of 'price'.
     *
     * @param price -> double value to be set as 'price'.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /***
     * Sets product's minimum inventory equal to that of 'min'.
     *
     * @param min -> int value to be set as 'min'.
     *            Represents minimum inventory of product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /***
     * Sets product's maximum inventory equal to that of 'max'.
     *
     * @param max -> int value to be set as 'max'.
     *            Represents maximum inventory of product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /***
     * Returns product's id.
     *
     */
    public int getId() {
        return id;
    }

    /***
     * Returns product's name.
     *
     */
    public String getName() {
        return name;
    }

    /***
     * Returns product's price.
     *
     */
    public double getPrice() {
        return price;
    }

    /***
     * Returns product's stock/inventory.
     *
     */
    public int getStock() {
        return stock;
    }

    /***
     * Returns product's minimum inventory.
     *
     */
    public int getMin() {
        return min;
    }

    /***
     * Returns product's maximum inventory.
     *
     */
    public int getMax() {
        return max;
    }

}
