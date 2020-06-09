package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class DescUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("description")
    String Description;

    public DescUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
