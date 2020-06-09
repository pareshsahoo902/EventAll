package com.otta.eventall.Activities.ViewBusiness.Methods;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;

import java.util.List;

public class SingleBusiness_RESPONSE {
    @SerializedName("status")
    boolean Status;

    @SerializedName("data")
    BusinessModel SingleBusiness;


    public SingleBusiness_RESPONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public BusinessModel getSingleBusiness() {
        return SingleBusiness;
    }

    public void setSingleBusiness(BusinessModel singleBusiness) {
        SingleBusiness = singleBusiness;
    }
}
