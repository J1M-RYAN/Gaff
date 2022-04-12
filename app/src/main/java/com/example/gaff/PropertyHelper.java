package com.example.gaff;

public class PropertyHelper {
    private String addressLine1, addressLine2;
    private int pricePerMonth;

    public PropertyHelper(){
        // required no arg constructor
    }
    public PropertyHelper(String addressLine1, String addressLine2, int pricePerMonth){
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

    public int getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(int pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }


}
