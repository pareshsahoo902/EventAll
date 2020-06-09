package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class SubCategoryUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("subcategory")
    String SubCategory;

    public SubCategoryUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }
}
