package com.creppyfm.inventorymanagementapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /***
     * Adds 'newPart' to 'allParts' list.
     *
     * @param newPart -> new part to be added to 'allParts' list.
     */
    public static void addPart (Part newPart) {
        allParts.add(newPart);
    }

    /***
     * Adds 'newProduct' to 'allProducts' list.
     *
     * @param newProduct -> new part to be added to 'allProducts' list.
     */
    public static void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }

    /***
     * Gets index of 'selectedPart'.
     * Sets 'allParts[index]' to updated values of 'selectedPart'.
     *
     * @param selectedPart -> part to be modified.
     */
    public static void updatePart (Part selectedPart) {
        int index = indexOfPartOrProduct(selectedPart.getId(), selectedPart);
        allParts.set(index, selectedPart);
    }

    /***
     * Returns int 'index' to be used to update parts/products.
     * Uses 'id' as criteria to search list allParts/allProducts for match.
     * Gets index of matching result to be used in allParts/allProducts.updatePart/updateProduct.
     *
     * @param id -> int id of part/product.
     * @param object -> generic object to represent parts/products passed to method.
     */
    public static int indexOfPartOrProduct(int id, Object object) {
        int index = -1;
        if (object instanceof Part) {
            for (int i = 0; i < allParts.size(); i++) {
                Part part = allParts.get(i);
                if (part.getId() == id) {
                    index = i;
                }
            }
        } else if (object instanceof Product) {
            for (int i = 0; i < allProducts.size(); i++) {
                Product product = allProducts.get(i);
                if (product.getId() == id) {
                    index = i;
                }
            }
        }
        return index;
    }

    /***
     * Returns 'true' if 'allParts' has successfully removed 'selectedPart', and 'false' if not.
     * Sets 'allParts[index]' to updated values of 'selectedPart'.
     *
     * @param selectedPart -> part to be removed from 'allParts'.
     */
    public static boolean deletePart (Part selectedPart) {
        return allParts.removeAll(selectedPart);
    }

    /***
     * Returns Observable List containing all parts in 'allParts'.
     * Sets 'allParts[index]' to updated values of 'selectedPart'.
     *
     */
    public static ObservableList<Part> getAllParts () {
        return allParts;
    }

    /***
     * Returns Product from 'allProducts' with mathcing 'productID'.
     *
     * @param productID -> used as criteria to select product from 'allProducts'.
     */
    public static Product lookupProduct (int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /***
     * Returns Product from 'allProducts' with mathcing 'productName'.
     *
     * @param productName -> used as criteria to select product from 'allProducts'.
     */
    public static Product lookupProduct (String productName) {
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    /***
     * Gets index of 'newProduct'.
     * Sets 'allProducts[index]' to updated values of 'newProduct'.
     *
     * @param newProduct -> product to be modified.
     */
    public static void updateProduct (Product newProduct) {
        int index = indexOfPartOrProduct(newProduct.getId(), newProduct);
        allProducts.set(index, newProduct);
    }

    /***
     * Returns 'true' if 'allProducts' has successfully removed 'selectedProduct', and 'false' if not.
     * Sets 'allProducts[index]' to updated values of 'selectedProduct'.
     *
     * @param selectedProduct -> product to be removed from 'allProducts'.
     */
    public static boolean deleteProduct (Product selectedProduct) {
        return allProducts.removeAll(selectedProduct);
    }

    /***
     * Returns Observable List containing all products in 'allProducts'.
     *
     */
    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }

}


