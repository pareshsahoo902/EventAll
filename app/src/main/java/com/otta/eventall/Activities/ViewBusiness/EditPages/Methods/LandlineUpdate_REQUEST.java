package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class LandlineUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("landline_no")
    String LandLineNumber;

    public LandlineUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getLandLineNumber() {
        return LandLineNumber;
    }

    public void setLandLineNumber(String landLineNumber) {
        LandLineNumber = landLineNumber;
    }
}
