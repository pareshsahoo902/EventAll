package com.otta.eventall.Activities.Search.Methods;

import com.google.gson.annotations.SerializedName;

public class Search_REQUEST {

    @SerializedName("subcategory")
    String SubCategory;

    @SerializedName("location")
    String Location;

    public Search_REQUEST() {
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
