package com.creppyfm.inventorymanagementapp.model;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id,
                    String name,
                    double price,
                    int stock,
                    int min,
                    int max,
                    String companyName) {
        super(id,
                name,
                price,
                stock,
                min,
                max);


        this.companyName = companyName;
    }

    /***
     * Sets value of 'companyName' equal to that of parameter 'companyName'.
     *
     * @param companyName -> string value 'companyName'.
     */
    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    /***
     * Returns value of 'companyName'.
     *
     */
    public String getCompanyName () {
        return companyName;
    }
}
