package com.example.gaff;

public class Property {
    private String addressLine1, addressLine2, eircode, pricePerMonth, propertyType, bedrooms, bathrooms, title, privateParking;

    public Property(){
        // required no arg constructor
    }
    public Property(String addressLine1, String addressLine2, String pricePerMonth, String eircode, String propertyType,
                    String bedrooms, String bathrooms, String title, String privateParking){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.pricePerMonth = pricePerMonth;
        this.eircode = eircode;
        this.propertyType = propertyType;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.title = title;
        this.privateParking = privateParking;
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

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrivateParking() {
        return privateParking;
    }

    public void setPrivateParking(String privateParking) {
        this.privateParking = privateParking;
    }
}
