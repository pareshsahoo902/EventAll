package com.otta.eventall.Activities.ViewBusiness.Methods;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.Model.CategoryModel;

import java.util.List;

public class AllBusinesses_RESPONSE {
    @SerializedName("status")
    boolean Status;

    @SerializedName("data")
    List<BusinessModel> AllBusinessessList;


    public AllBusinesses_RESPONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public List<BusinessModel> getAllBusinessessList() {
        return AllBusinessessList;
    }

    public void setAllBusinessessList(List<BusinessModel> allBusinessessList) {
        AllBusinessessList = allBusinessessList;
    }
}
