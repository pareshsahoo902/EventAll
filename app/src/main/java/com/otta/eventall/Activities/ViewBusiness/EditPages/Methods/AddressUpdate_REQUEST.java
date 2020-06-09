package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class AddressUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("building_name")
    String BuildingName;
    @SerializedName("street_name")
    String StreetName;
    @SerializedName("locality")
    String Area;
    @SerializedName("city")
    String City;
    @SerializedName("dist")
    String Dist;
    @SerializedName("state")
    String State;
    @SerializedName("country")
    String Country;
    @SerializedName("pincode")
    String Pincode;
    @SerializedName("landmark")
    String Landmark;
    @SerializedName("coordinates")
    String Coordinates;

    public AddressUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String BuildingN) {
        BuildingName = BuildingN;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDist() {
        return Dist;
    }

    public void setDist(String dist) {
        Dist = dist;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }
}
