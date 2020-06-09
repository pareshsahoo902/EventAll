package com.otta.eventall.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {
    @SerializedName("category_id")
    int CatID;

    @SerializedName("category_name")
    String CatName;

    @SerializedName("category_image")
    String CatImage;

    @SerializedName("sucategories_list")
    List<SubCategoryModel> subCategoryList;


    public CategoryModel() {
    }

    public int getCatID() {
        return CatID;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getCatImage() {
        return CatImage;
    }

    public void setCatImage(String catImage) {
        CatImage = catImage;
    }

    public List<SubCategoryModel> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryModel> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
