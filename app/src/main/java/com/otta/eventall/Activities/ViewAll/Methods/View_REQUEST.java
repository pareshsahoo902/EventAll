package com.otta.eventall.Activities.ViewAll.Methods;

import com.google.gson.annotations.SerializedName;

public class View_REQUEST {

    @SerializedName("subcategory")
    String SubCategory;


    public View_REQUEST() {
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

}
