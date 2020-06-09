package com.otta.eventall;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Model.CategoryModel;

import java.util.List;

public class AllCategory_RESPONSE {
    @SerializedName("status")
    boolean Status;

    @SerializedName("categories_list")
    List<CategoryModel> CategoryList;


    public AllCategory_RESPONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public List<CategoryModel> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<CategoryModel> categoryList) {
        CategoryList = categoryList;
    }


}
