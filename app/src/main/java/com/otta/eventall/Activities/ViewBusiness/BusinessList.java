package com.otta.eventall.Activities.ViewBusiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.AddBusiness.Register_Business;
import com.otta.eventall.Activities.Home.Home;
import com.otta.eventall.Activities.Login.Methods.Logout_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Adapter.BusinessList_Adapter;
import com.otta.eventall.Activities.ViewBusiness.Methods.AllBusinesses_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.SlashScreen;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessList extends AppCompatActivity {

    BusinessList_Adapter adapter;
    RecyclerView recyclerView;
    ImageView Back;
    CardView AddANewBusiness;
    FrameLayout EmptyFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());
        Init();
        Clickers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FetchAllBusinesses();
    }

    void Init() {

        EmptyFrame =  findViewById(R.id.EmptyFrame);
        Back = findViewById(R.id.BackChal);
        AddANewBusiness = findViewById(R.id.Card_AddABusiness);
        recyclerView = findViewById(R.id.recyclerviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        EmptyFrame.setVisibility(View.GONE);
    }

    void Clickers(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddANewBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NewBusiness = new Intent(getApplicationContext() , Register_Business.class);
                startActivity(NewBusiness);
            }
        });
    }


    void SetAdapter(List<BusinessModel> list) {
        adapter = new BusinessList_Adapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);
    }


    void FetchAllBusinesses() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(BusinessList.this, "Loading Businesses");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<AllBusinesses_RESPONSE> call = api.FetchAllBusinesses("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()));
        call.enqueue(new Callback<AllBusinesses_RESPONSE>() {
            @Override
            public void onResponse(Call<AllBusinesses_RESPONSE> call, Response<AllBusinesses_RESPONSE> response) {
                dialog.dismiss();
                AllBusinesses_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    EmptyFrame.setVisibility(View.GONE);
                    SetAdapter(response1.getAllBusinessessList());

                } else {
                    EmptyFrame.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<AllBusinesses_RESPONSE> call, Throwable t) {

                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please check your internet connection", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });


    }
}
