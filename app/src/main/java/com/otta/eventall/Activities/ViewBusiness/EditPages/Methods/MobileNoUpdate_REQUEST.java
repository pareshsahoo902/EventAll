package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class MobileNoUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("mobile_no")
    String MobileNo;

    public MobileNoUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
