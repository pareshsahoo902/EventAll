package com.otta.eventall.Model;

import com.google.gson.annotations.SerializedName;

public class SubCategoryModel {
    @SerializedName("subcategory_id")
    int SubCatID;

    @SerializedName("subcategory_name")
    String SubCatName;

    @SerializedName("subcategory_image")
    String SubCatImage;


    public SubCategoryModel() {

    }

    public int getSubCatID() {
        return SubCatID;
    }

    public void setSubCatID(int subCatID) {
        SubCatID = subCatID;
    }

    public String getSubCatName() {
        return SubCatName;
    }

    public void setSubCatName(String subCatName) {
        SubCatName = subCatName;
    }

    public String getSubCatImage() {
        return SubCatImage;
    }

    public void setSubCatImage(String subCatImage) {
        SubCatImage = subCatImage;
    }
}
