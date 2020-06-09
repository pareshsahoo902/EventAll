package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class Open24hrUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("open_24hr")
    int Open24Hr;

    public Open24hrUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public int getOpen24Hr() {
        return Open24Hr;
    }

    public void setOpen24Hr(int open24Hr) {
        Open24Hr = open24Hr;
    }
}
