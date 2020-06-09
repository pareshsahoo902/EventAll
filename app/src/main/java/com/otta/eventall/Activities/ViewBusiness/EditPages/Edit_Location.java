package com.otta.eventall.Activities.ViewBusiness.EditPages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.AddressUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Location extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    CardView SaveButton;
    EditText Edit_BuildingName, Edit_Street, Edit_Area,
            Edit_City, Edit_Dist, Edit_State, Edit_Pincode, Edit_Landmark;

    CardView MapCard;
    ImageView MapClickPanel;
    String PickedLocation = null;
    ImageView Back;
    String _BusinessID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__location);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());


        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");


        Init();
        SetValues();

        MapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateAllFields()) {

                    Update();

                } else {
                    Toast.makeText(getApplicationContext(), "Please Provide All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MapClickPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent oin = new Intent(getApplicationContext(), PickLocation.class);
                oin.putExtra("name", Edit_BuildingName.getText().toString() +","+ Edit_Area.getText().toString() +
                       ","+ Edit_City.getText().toString() +","+Edit_State.getText().toString());
                startActivityForResult(oin, 45);


            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void Init() {


        SupportMapFragment supportmapfragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapfragment.getMapAsync(this);

        SaveButton = findViewById(R.id.SaveButton);
        Back = findViewById(R.id.backAss);

        Edit_BuildingName = findViewById(R.id.Edit_BuildingName);
        Edit_Street = findViewById(R.id.Edit_StreetName);
        Edit_Area = findViewById(R.id.Edit_Area);
        Edit_City = findViewById(R.id.Edit_City);
        Edit_Dist = findViewById(R.id.Edit_District);
        Edit_State = findViewById(R.id.Edit_State);
        Edit_Pincode = findViewById(R.id.Edit_PinCode);
        Edit_Landmark = findViewById(R.id.Edit_landmark);
        MapCard = findViewById(R.id.MapCard);
        MapClickPanel = findViewById(R.id.MapClickPanel);
    }


    void SetValues() {

        Bundle bundle = getIntent().getExtras();

        Edit_BuildingName.setText(bundle.getString("Building"));
        Edit_Street.setText(bundle.getString("Street"));
        Edit_Area.setText(bundle.getString("Area"));
        Edit_City.setText(bundle.getString("City"));
        Edit_Dist.setText(bundle.getString("Dist"));
        Edit_State.setText(bundle.getString("State"));
        Edit_Pincode.setText(bundle.getString("Pin"));
        Edit_Landmark.setText(bundle.getString("Landmark"));
        PickedLocation = bundle.getString("latlon");
    }

    boolean ValidateAllFields() {

        if (Edit_BuildingName.getText().length() > 0 &&
                //Edit_Street.getText().length() > 0 &&
                Edit_Area.getText().length() > 0 &&
                Edit_City.getText().length() > 0 &&
                //Edit_Dist.getText().length() > 0 &&
                Edit_State.getText().length() > 0 &&
                Edit_Pincode.getText().length() > 0 &&
                //Edit_Landmark.getText().length() > 0 &&
                PickedLocation != null) {

            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 45) {
            if (resultCode == Activity.RESULT_OK) {
                PickedLocation = data.getStringExtra("points");
                String[] SpiledtedAltLong = PickedLocation.split(",");

                LatLng latLng = new LatLng(Double.parseDouble(SpiledtedAltLong[0]), Double.parseDouble(SpiledtedAltLong[1]));
                map.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)));

            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }


    void Update() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_Location.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressUpdate_REQUEST request = new AddressUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        request.setBuildingName(Edit_BuildingName.getText().toString());
        request.setStreetName("nothing");//Edit_Street.getText().toString());
        request.setArea(Edit_Area.getText().toString());
        request.setCity(Edit_City.getText().toString());
        request.setDist("nothing");
        request.setState(Edit_State.getText().toString());
        request.setPincode(Edit_Pincode.getText().toString());
        request.setCountry("India");
        request.setLandmark("nothing");//Edit_Landmark.getText().toString());
        request.setCoordinates(PickedLocation);

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_Address("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    Toast.makeText(getApplicationContext(), "Address Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update. Check the address again and try again", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.setDuration(100000);
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<UniversalUpdate_RESPONSE> call, Throwable t) {
                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please check your internet connection", Snackbar.LENGTH_LONG);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Update();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }
}
