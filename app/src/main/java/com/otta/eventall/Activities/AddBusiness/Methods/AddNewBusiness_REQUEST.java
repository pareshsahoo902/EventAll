package com.otta.eventall.Activities.AddBusiness.Methods;

import com.google.gson.annotations.SerializedName;

public class AddNewBusiness_REQUEST {


    @SerializedName("title")
    String BusinessTitle;

    @SerializedName("contact_person")
    String ContactPerson;

    @SerializedName("mobile_no")
    String MobileNumber;

    @SerializedName("open_24hr")
    int Open24Hrs;

    @SerializedName("open_hours")
    String OpenHrs;

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
    String PinCode;

    @SerializedName("landmark")
    String Landmark;

    @SerializedName("coordinates")
    String Coordinates;

    public AddNewBusiness_REQUEST() {
    }

    public String getBusinessTitle() {
        return BusinessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        BusinessTitle = businessTitle;
    }


    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }


    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }


    public int getOpen24Hrs() {
        return Open24Hrs;
    }

    public void setOpen24Hrs(int open24Hrs) {
        Open24Hrs = open24Hrs;
    }


    public String getOpenHrs() {
        return OpenHrs;
    }

    public void setOpenHrs(String openHrs) {
        OpenHrs = openHrs;
    }


    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
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


    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
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
