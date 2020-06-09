package com.otta.eventall.Activities.AddBusiness;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.AddBusiness.Methods.AddNewBusiness_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Business_Address extends AppCompatActivity {

    CardView SaveButton;
    ImageView Back;

    EditText BuildingName;
    EditText StreetName;
    EditText Area;
    EditText City;
    EditText Dist;
    EditText StateName;
    EditText PinCode;
    EditText Landmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__address);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Clickers();

    }

    void Init() {

        Back = findViewById(R.id.BackImage);
        SaveButton = findViewById(R.id.SaveButton);

        BuildingName = findViewById(R.id.Edit_BuildingName);
        StreetName = findViewById(R.id.Edit_StreetName);
        Area = findViewById(R.id.Edit_Area);
        City = findViewById(R.id.Edit_City);
        Dist = findViewById(R.id.Edit_Dist);
        StateName = findViewById(R.id.Edit_State);
        PinCode = findViewById(R.id.Edit_PinCode);
        Landmark = findViewById(R.id.Edit_landmark);
    }


    void Clickers() {
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidateFields()){
                    SaveDetailsinRequest();
                }else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please fill all the fields", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });
    }


    boolean ValidateFields() {
        if (BuildingName.getText().length() > 0 &&
                //StreetName.getText().length() > 0 &&
                Area.getText().length() > 0 &&
                City.getText().length() > 0 &&
                //Dist.getText().length() > 0 &&
                StateName.getText().length() > 0  &&
                PinCode.getText().length() > 0 ){
                //Landmark.getText().length() > 0) {
            return true;
        } else return false;
    }



    void SaveDetailsinRequest() {

        ArrayPlace.addNewBusiness_request.setBuildingName(BuildingName.getText().toString());
        ArrayPlace.addNewBusiness_request.setStreetName("nothing");
        ArrayPlace.addNewBusiness_request.setArea(Area.getText().toString());
        ArrayPlace.addNewBusiness_request.setCity(City.getText().toString());
        ArrayPlace.addNewBusiness_request.setDist("nothing");//Dist.getText().toString());
        ArrayPlace.addNewBusiness_request.setState(StateName.getText().toString());
        ArrayPlace.addNewBusiness_request.setPinCode(PinCode.getText().toString());
        ArrayPlace.addNewBusiness_request.setLandmark("nothing");//Landmark.getText().toString());
        ArrayPlace.addNewBusiness_request.setCountry("India");
        ArrayPlace.addNewBusiness_request.setCoordinates("0.00,0.00");

        //Add  Timing
        ArrayPlace.addNewBusiness_request.setOpen24Hrs(1);

        String TimingString =  "10:00 AM,05:00 PM/10:00 AM,05:00 PM/10:00 AM,05:00 PM/10:00 AM,05:00 PM/10:00 AM,05:00 PM/10:00 AM,05:00 PM/10:00 AM,05:00 PM" ;
        ArrayPlace.addNewBusiness_request.setOpenHrs(TimingString);

       CreateBusiness();
    }


    void CreateBusiness() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Business_Address.this, "Adding Business");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<AddNewBusiness_RESPONSE> call = api.AddANewBusiness("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()),
                ArrayPlace.addNewBusiness_request);

        call.enqueue(new Callback<AddNewBusiness_RESPONSE>() {
            @Override
            public void onResponse(Call<AddNewBusiness_RESPONSE> call, Response<AddNewBusiness_RESPONSE> response) {

                dialog.dismiss();

                AddNewBusiness_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    Intent Details = new Intent(getApplicationContext(), SuccessfullyBusinessAdded.class);
                    startActivity(Details);
                    finish();

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to add a new Business. Please try again.", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(100000);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CreateBusiness();
                        }
                    });
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<AddNewBusiness_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to add a new Business. Please try again.", Snackbar.LENGTH_LONG);
                snackbar.setDuration(100000);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateBusiness();
                    }
                });
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();

            }
        });
    }

}
