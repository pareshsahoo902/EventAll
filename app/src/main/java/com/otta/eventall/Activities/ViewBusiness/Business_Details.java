package com.otta.eventall.Activities.ViewBusiness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_BusinessTitle;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Description;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Location;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import mumayank.com.airlocationlibrary.AirLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Business_Details extends AppCompatActivity implements OnMapReadyCallback {

    ImageView BackImage;
    FrameLayout Edit_TagLine, Edit_Desc, UpdateLocation;
    GoogleMap map;
    AirLocation airLocation;
    Marker CurrentMarker;
    Location CurrentLocation;
    Bitmap smallMarker;
    MarkerOptions markerOptions;
    TextView NoPhotos, NoTagLine, NoIntroduction, NoLocation;
    TextView BusinessName, TagLine, Introduction, Text_Address;
    CardView MapContainer;
    TextView Text_ManagePhoto, Text_EditTagLine, Text_EditDescription, Text_UpdateLocation;
    String _BusinessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__details);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());
        Init();
        Clickers();

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

        FetchBusinessDetails(_BusinessID);

    }

    void Init() {

        Edit_TagLine = findViewById(R.id.Edit_TagLineFrame);
        Edit_Desc = findViewById(R.id.Edit_Desc_Frame);
        UpdateLocation = findViewById(R.id.Update_Location_Frame);
        BackImage = findViewById(R.id.BackImageSalSal);

        NoPhotos = findViewById(R.id.No_Image);
        NoTagLine = findViewById(R.id.No_TagLine);
        NoIntroduction = findViewById(R.id.No_Desc);
        NoLocation = findViewById(R.id.No_Location);

        BusinessName = findViewById(R.id.Text_BusinessName);
        TagLine = findViewById(R.id.Text_TagLine);
        Introduction = findViewById(R.id.Text_Intro);
        Text_Address = findViewById(R.id.Text_Address);

        MapContainer = findViewById(R.id.MapContainer);

        Text_ManagePhoto = findViewById(R.id.Text_ManagePhoto);
        Text_EditTagLine = findViewById(R.id.Text_EditTagLine);
        Text_EditDescription = findViewById(R.id.Text_EditDescription);
        Text_UpdateLocation = findViewById(R.id.Text_UpdateLocation);


        SupportMapFragment supportmapfragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapfragment.getMapAsync(this);
    }

    void Clickers() {

        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Edit_TagLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Edit_BusinessTitle.class);
                startActivity(intent);
            }
        });


        Edit_Desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Edit_Description.class);
                startActivity(intent);


            }
        });

        UpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent on = new Intent(getApplicationContext(), Edit_Location.class);
                startActivity(on);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LoadBusinessDeatils();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);


        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_pin);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        // Fetch location simply like this whenever you need
        FetchCurrentLocation();

    }

    void FetchCurrentLocation() {
        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(Location location) {
                // do something

                CurrentLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Business Name");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                CurrentMarker = map.addMarker(markerOptions);

                //Place current location marker
                //move map camera
                map.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)));

            }

            @Override
            public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
                Toast.makeText(getApplicationContext(), "Failed to get Current Location", Toast.LENGTH_SHORT).show();
                FetchCurrentLocation();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    void LoadBusinessDeatils(BusinessModel businessModel) {

//        if (OttaDB.get_TagLine(getApplicationContext()) != null) {
//            NoTagLine.setVisibility(View.GONE);
//            TagLine.setVisibility(View.VISIBLE);
//            TagLine.setText(OttaDB.get_TagLine(getApplicationContext()));
//            Text_EditTagLine.setText("EDIT TAG LINE");
//        } else {
//            NoTagLine.setVisibility(View.VISIBLE);
//            TagLine.setVisibility(View.GONE);
//            Text_EditTagLine.setText("ADD TAG LINE");
//        }


        if (businessModel.getDesc() != null) {
            NoIntroduction.setVisibility(View.GONE);
            Introduction.setVisibility(View.VISIBLE);
            Introduction.setText(businessModel.getDesc());
            Text_EditDescription.setText("EDIT DESCRIPTION");
        } else {
            NoIntroduction.setVisibility(View.VISIBLE);
            Introduction.setVisibility(View.GONE);
            Text_EditDescription.setText("ADD DESCRIPTION");
        }


        if (!businessModel.getCoordinates().equals("0.00,0.00")) {
            NoLocation.setVisibility(View.GONE);
            MapContainer.setVisibility(View.VISIBLE);
            Text_Address.setVisibility(View.VISIBLE);
            Text_UpdateLocation.setText("UPDATE LOCATION");
        } else {
            NoLocation.setVisibility(View.VISIBLE);
            MapContainer.setVisibility(View.GONE);
            Text_Address.setVisibility(View.GONE);
            Text_UpdateLocation.setText("ADD LOCATION");
        }
    }


    void FetchBusinessDetails(String BusinessID) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Business_Details.this, "Loading Details");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SingleBusiness_REQUEST request = new SingleBusiness_REQUEST();
        request.setBusinessID(BusinessID);

        API api = retrofit.create(API.class);
        Call<SingleBusiness_RESPONSE> call = api.FetchSingleBusiness("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext())
                , request);

        call.enqueue(new Callback<SingleBusiness_RESPONSE>() {
            @Override
            public void onResponse(Call<SingleBusiness_RESPONSE> call, Response<SingleBusiness_RESPONSE> response) {
                dialog.dismiss();

                SingleBusiness_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    LoadBusinessDeatils(response1.getSingleBusiness());

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to load business details. Please try again", Snackbar.LENGTH_LONG);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FetchBusinessDetails(_BusinessID);
                        }
                    });
                    snackbar.setDuration(100000);
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<SingleBusiness_RESPONSE> call, Throwable t) {

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

