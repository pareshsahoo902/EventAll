package com.otta.eventall.Utils;

import com.otta.eventall.Activities.AddBusiness.Methods.AddNewBusiness_REQUEST;
import com.otta.eventall.Model.CategoryModel;

import java.util.List;

public class ArrayPlace {

    public static List<CategoryModel> AllCategoryList;

    public static AddNewBusiness_REQUEST addNewBusiness_request;


    public static void Init_AddBusinessRequest(){
        if(addNewBusiness_request == null)
            addNewBusiness_request = new AddNewBusiness_REQUEST();
    }

}
