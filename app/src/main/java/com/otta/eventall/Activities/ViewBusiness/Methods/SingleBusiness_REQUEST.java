package com.otta.eventall.Activities.ViewBusiness.Methods;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;

public class SingleBusiness_REQUEST {
    @SerializedName("business_id")
    String BusinessID;


    public SingleBusiness_REQUEST() {
    }

    public String getBusinessID() {
        return BusinessID;
    }

    public void setBusinessID(String businessID) {
        BusinessID = businessID;
    }
}
