package com.example.gaff;

public class Property {
    private String addressLine1, addressLine2;
    private String pricePerMonth;

    public Property(){
        // required no arg constructor
    }
    public Property(String addressLine1, String addressLine2, String pricePerMonth){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.pricePerMonth = pricePerMonth;
    }

    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(String pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

}
