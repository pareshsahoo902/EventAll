package com.otta.eventall.Activities.ViewBusiness.EditPages;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mumayank.com.airlocationlibrary.AirLocation;

public class PickLocation extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    AirLocation airLocation;
    CardView MyLocation;
    TextView Lat, Lon;
    ImageView backButtton;

    long lat , lon;

    LatLng pickedLocation;

    CardView ConfirmLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        SupportMapFragment supportmapfragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapfragment.getMapAsync(this);

        Lat = findViewById(R.id.LatText);
        Lon = findViewById(R.id.LonText);

        Bundle bundle = getIntent().getExtras();
        lat = bundle.getLong("lat");
        lon = bundle.getLong("lon");



        backButtton = findViewById(R.id.back);
        MyLocation = findViewById(R.id.MyLoctionbutton);

        backButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ConfirmLocation = findViewById(R.id.ConfirmLocation);

        ConfirmLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (OttaDB.get_BusinessInfo(getApplicationContext()) == null) {
//
//                    BusinessModel model = new BusinessModel();
//                    LocationModel locationModel = new LocationModel();
//
//                    locationModel.setLongitude(pickedLocation.longitude);
//                    locationModel.setLatitude(pickedLocation.latitude);
//
//                   // model.setLocationInfo(locationModel);
//
//                    OttaDB.save_BusinessInfo(getApplicationContext(), model);
//
//
//                } else {
//
//                    BusinessModel model = OttaDB.get_BusinessInfo(getApplicationContext());
//                   // model.getLocationInfo().setLongitude(pickedLocation.longitude);
//                   // model.getLocationInfo().setLatitude(pickedLocation.latitude);
//                    OttaDB.save_BusinessInfo(getApplicationContext(), model);
//                }

                Intent returnResultIntent = new Intent();
                returnResultIntent.putExtra("points" , pickedLocation.latitude + "," + pickedLocation.longitude);
                setResult(Activity.RESULT_OK , returnResultIntent);
                finish();


            }
        });


        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchCurrentLocation();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Bundle bundle=  getIntent().getExtras();

        try {
            String location = bundle.getString("name");
            Geocoder gc = new Geocoder(PickLocation.this);
            List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

            List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
            for(Address a : addresses){
                if(a.hasLatitude() && a.hasLongitude()){
                    ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                }
            }

            if(ll.size() > 0)
                map.animateCamera((CameraUpdateFactory.newLatLngZoom(ll.get(0), 18.0f)));
            else{
                FetchCurrentLocation();
                Toast.makeText(getApplicationContext() , "No coordinates found for this address. Please select coordinates manually.", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            // handle the exception
            FetchCurrentLocation();
            Toast.makeText(getApplicationContext() , "No coordinates found for this address. Please select coordinates manually.", Toast.LENGTH_LONG).show();
        }



        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                pickedLocation = map.getCameraPosition().target;
                Lat.setText("Lat : " + pickedLocation.latitude);
                Lon.setText("Lon : " + pickedLocation.longitude);

            }
        });


    }

    void FetchCurrentLocation() {
        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(Location location) {
                // do something

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                pickedLocation = latLng;

                //move map camera
                map.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)));

                Toast.makeText(getApplicationContext(), "Location set to Current Location", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
                Toast.makeText(getApplicationContext(), "Failed to get Current Location", Toast.LENGTH_SHORT).show();
                FetchCurrentLocation();
            }
        });
    }


}
