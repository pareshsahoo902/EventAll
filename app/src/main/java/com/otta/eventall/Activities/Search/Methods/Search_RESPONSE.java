package com.otta.eventall.Activities.Search.Methods;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;

import java.util.List;

public class Search_RESPONSE {

    @SerializedName("status")
    boolean Status;

    @SerializedName("data")
    List<BusinessModel>  resultList;

    public Search_RESPONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public List<BusinessModel> getResultList() {
        return resultList;
    }

    public void setResultList(List<BusinessModel> resultList) {
        this.resultList = resultList;
    }
}
