package com.otta.eventall.Activities.ViewBusiness.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessModel implements Serializable {

    @SerializedName("business_id")
    String BusinessID;
    @SerializedName("title")
    String BusinessTitle;
    @SerializedName("contact_person")
    String CotactPersonName;
    @SerializedName("mobile_no")
    String MobileNumber;
    @SerializedName("landline_no")
    String LandLineNumber;
    @SerializedName("category")
    String Category;
    @SerializedName("subcategory")
    String SubCategory;
    @SerializedName("est_year")
    String EstablishmentYear;
    @SerializedName("descriptions")
    String Desc;
    @SerializedName("photos")
    String Photos;
    @SerializedName("open_24hr")
    int Open24Hr;
    @SerializedName("open_hours")
    String OpenHours;
    @SerializedName( "building_name")
    String BuildingName;
    @SerializedName( "street_name")
    String Street;
    @SerializedName("locality")
    String Area;
    @SerializedName( "city")
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


    public BusinessModel() {

    }

    public String getBusinessID() {
        return BusinessID;
    }

    public void setBusinessID(String businessID) {
        BusinessID = businessID;
    }

    public String getBusinessTitle() {
        return BusinessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        BusinessTitle = businessTitle;
    }

    public String getCotactPersonName() {
        return CotactPersonName;
    }

    public void setCotactPersonName(String cotactPersonName) {
        CotactPersonName = cotactPersonName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getLandLineNumber() {
        return LandLineNumber;
    }

    public void setLandLineNumber(String landLineNumber) {
        LandLineNumber = landLineNumber;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getEstablishmentYear() {
        return EstablishmentYear;
    }

    public void setEstablishmentYear(String establishmentYear) {
        EstablishmentYear = establishmentYear;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }

    public int getOpen24Hr() {
        return Open24Hr;
    }

    public void setOpen24Hr(int open24Hr) {
        Open24Hr = open24Hr;
    }

    public String getOpenHours() {
        return OpenHours;
    }

    public void setOpenHours(String openHours) {
        OpenHours = openHours;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
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
