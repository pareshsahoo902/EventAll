package com.otta.eventall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_BusinessTitle;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Description;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Location;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Photos;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewDesign extends AppCompatActivity implements OnMapReadyCallback {

    TextView TV_BusinessTitle, TV_ContactDetails, TV_Address, TV_Timing;
    TextView TV_Desc, Text_GiveSomePhotos;

    ImageView Back;
    TextView Text_SelectCategory;
    ChipGroup chipGroup;
    CardView Edit_Photos;
    CardView Edit_Title, Edit_Desc, Edit_ContactDetails, Edit_Timing, Edit_Address, Edit_Category, MapCard;
    String _BusinessID;
    FrameLayout WhitePanel;

    BusinessModel businessModel;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details_new);
        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());
        Init();
        Clickers();

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");
        FetchBusinessDetails(_BusinessID);
    }

    void Init() {
        SupportMapFragment supportmapfragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapfragment.getMapAsync(this);


        TV_BusinessTitle = findViewById(R.id.TV_BusinessTitle);
        TV_ContactDetails = findViewById(R.id.TV_ContactDetails);
        TV_Address = findViewById(R.id.TV_Address);
        TV_Desc = findViewById(R.id.TV_Description);
        Text_GiveSomePhotos = findViewById(R.id.Text_GiveSomePhotos);
        TV_Timing = findViewById(R.id.TV_Timing);
        Edit_Desc = findViewById(R.id.Edit_Desc);
        Edit_Title = findViewById(R.id.Edit_Title);
        Edit_ContactDetails = findViewById(R.id.Edit_ContactDetails);
        Edit_Timing = findViewById(R.id.Edit_Timings);
        Edit_Address = findViewById(R.id.Edit_Address);
        Edit_Category = findViewById(R.id.Edit_Category);
        Edit_Photos = findViewById(R.id.Edit_Photos);
        chipGroup = findViewById(R.id.chipGroup);
        Text_SelectCategory = findViewById(R.id.Text_SelectCategory);
        WhitePanel = findViewById(R.id.WhitePanel);
        WhitePanel.setVisibility(View.VISIBLE);
        MapCard = findViewById(R.id.MapCard);
        Back = findViewById(R.id.BackImageSalSal);


    }

    void Clickers() {
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Edit_Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), Edit_BusinessTitle.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("INFO", TV_BusinessTitle.getText().toString());
                startActivity(EditDesc);
            }
        });

        Edit_Desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), Edit_Description.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("DATA", businessModel.getDesc());
                startActivity(EditDesc);
            }
        });

        Edit_ContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_ContactDetails.class);
                EditDesc.putExtra("BID", _BusinessID);
                if (businessModel.getCotactPersonName() != null)
                    EditDesc.putExtra("INFO", businessModel.getCotactPersonName());
                if (businessModel.getMobileNumber() != null)
                    EditDesc.putExtra("INFO2", businessModel.getMobileNumber());
                if (businessModel.getLandLineNumber() != null)
                    EditDesc.putExtra("INFO3", businessModel.getLandLineNumber());
                startActivity(EditDesc);
            }
        });

        Edit_Timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Timing.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("is24x7", businessModel.getOpen24Hr());
                startActivity(EditDesc);
            }
        });

        Edit_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Category.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("CAT", businessModel.getSubCategory());
                startActivity(EditDesc);
            }
        });

        Edit_Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), Edit_Photos.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("PHOTOS", businessModel.getPhotos());
                startActivity(EditDesc);
            }
        });

        Edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditDesc = new Intent(getApplicationContext(), Edit_Location.class);
                EditDesc.putExtra("BID", _BusinessID);
                EditDesc.putExtra("Building", businessModel.getBuildingName());
                EditDesc.putExtra("Street", businessModel.getStreet());
                EditDesc.putExtra("Area", businessModel.getArea());
                EditDesc.putExtra("City", businessModel.getCity());
                EditDesc.putExtra("Dist", businessModel.getDist());
                EditDesc.putExtra("State", businessModel.getState());
                EditDesc.putExtra("Pin", businessModel.getPincode());
                EditDesc.putExtra("Landmark", businessModel.getLandmark());
                EditDesc.putExtra("latlon", businessModel.getCoordinates());
                startActivity(EditDesc);
            }
        });
    }

    void FetchBusinessDetails(String BusinessID) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(NewDesign.this, "Loading Details");
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
                    WhitePanel.setVisibility(View.GONE);
                    LoadBusinessDeatils(response1.getSingleBusiness());
                    businessModel = response1.getSingleBusiness();

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


    void SetChips(String SbuCatString) {

        String[] spilted = SbuCatString.split("@");

        chipGroup.removeAllViews();

        for (int i = 0; i < spilted.length; i++) {
            Chip newChip = getChip(chipGroup, spilted[i]);
            chipGroup.addView(newChip);
            FontEngine.overrideTypeface(getApplicationContext(), chipGroup);
        }


    }


    void LoadBusinessDeatils(BusinessModel businessModel) {


        if (businessModel.getSubCategory() != null) {
            Text_SelectCategory.setVisibility(View.GONE);
            SetChips(businessModel.getSubCategory());
        } else {
            Text_SelectCategory.setVisibility(View.VISIBLE);
        }


        //Title
        TV_BusinessTitle.setText(businessModel.getBusinessTitle());

        //Description
        if (businessModel.getDesc() == null)
            TV_Desc.setText("Describe your Business");
        else
            TV_Desc.setText(businessModel.getDesc());

        //Photos
        if (businessModel.getPhotos() == null)
            Text_GiveSomePhotos.setText("No Photos Available");
        else {
            String photoString = businessModel.getPhotos().replace("}}e", "");
            String[] SpiltedPhoto = photoString.split(",");
            if (SpiltedPhoto.length > 0)
                Text_GiveSomePhotos.setText(SpiltedPhoto.length + " Photos Available");
            else
                Text_GiveSomePhotos.setText("No Photos Available");
        }

        //Contact Details
        if (businessModel.getLandLineNumber() == null)
            TV_ContactDetails.setText(businessModel.getCotactPersonName() + "\n" + businessModel.getMobileNumber());
        else {
            if (businessModel.getLandLineNumber().equals("0000000000")) {
                TV_ContactDetails.setText(businessModel.getCotactPersonName() + "\n" +
                        businessModel.getMobileNumber());
            } else {
                TV_ContactDetails.setText(businessModel.getCotactPersonName() + "\n" +
                        businessModel.getMobileNumber() + ", " + businessModel.getLandLineNumber());
            }
        }


        //Timing
        if (businessModel.getOpen24Hr() == 1)
            TV_Timing.setText("Opens Everyday 24 Hours");
        else
            try {
                TV_Timing.setText(GetFormattedTiming(businessModel.getOpenHours()));
            } catch (Exception e) {
            }

        //Address

        if (businessModel.getCoordinates().equals("0.00,0.00")) {
            TV_Address.setText(businessModel.getBuildingName() + ", " +
                    //businessModel.getStreet() + ", " +
                    //businessModel.getLandmark() + ", " +
                    businessModel.getArea() + ", " +
                    businessModel.getCity() + ", " +
                    //businessModel.getDist() + ", " +
                    businessModel.getState() + ", " +
                    businessModel.getPincode()  +"\n\n" + "No Map Coordinates Available");
            MapCard.setVisibility(View.GONE);
        } else {
            TV_Address.setText(businessModel.getBuildingName() + ", " +
                            //businessModel.getStreet() + ", " +
                            //businessModel.getLandmark() + ", " +
                            businessModel.getArea() + ", " +
                            businessModel.getCity() + ", " +
                            //businessModel.getDist() + ", " +
                            businessModel.getState() + ", " +
                            businessModel.getPincode());

            MapCard.setVisibility(View.VISIBLE);

            String[] SpiledtedAltLong = businessModel.getCoordinates().split(",");

            LatLng latLng = new LatLng(Double.parseDouble(SpiledtedAltLong[0]), Double.parseDouble(SpiledtedAltLong[1]));
            map.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)));

        }

    }


    private String GetFormattedTiming(String TimingString) {
        String timing;
        String[] Spilted = TimingString.split("/");

        String[] SunSplit = Spilted[0].split(",");
        String[] MonSlit = Spilted[1].split(",");
        String[] TueSlit = Spilted[2].split(",");
        String[] WedSlit = Spilted[3].split(",");
        String[] ThuSlit = Spilted[4].split(",");
        String[] FriSlit = Spilted[5].split(",");
        String[] SatSlit = Spilted[6].split(",");

        String SunText;
        if (SunSplit[0].equals("Open 24 Hrs"))
            SunText = "Open 24 Hours";
        else if (SunSplit[0].equals("Closed"))
            SunText = "Closed";
        else
            SunText = SunSplit[0] + "  -  " + SunSplit[1];

        String MonText;
        if (MonSlit[0].equals("Open 24 Hrs"))
            MonText = "Open 24 Hours";
        else if (MonSlit[0].equals("Closed"))
            MonText = "Closed";
        else
            MonText = MonSlit[0] + "  -  " + MonSlit[1];

        String TueText;
        if (TueSlit[0].equals("Open 24 Hrs"))
            TueText = "Open 24 Hours";
        else if (TueSlit[0].equals("Closed"))
            TueText = "Closed";
        else
            TueText = TueSlit[0] + "  -  " + TueSlit[1];

        String WedText;
        if (WedSlit[0].equals("Open 24 Hrs"))
            WedText = "Open 24 Hours";
        else if (WedSlit[0].equals("Closed"))
            WedText = "Closed";
        else
            WedText = WedSlit[0] + "  -  " + WedSlit[1];

        String ThuText;
        if (ThuSlit[0].equals("Open 24 Hrs"))
            ThuText = "Open 24 Hours";
        else if (ThuSlit[0].equals("Closed"))
            ThuText = "Closed";
        else
            ThuText = ThuSlit[0] + "  -  " + ThuSlit[1];

        String FriText;
        if (FriSlit[0].equals("Open 24 Hrs"))
            FriText = "Open 24 Hours";
        else if (FriSlit[0].equals("Closed"))
            FriText = "Closed";
        else
            FriText = FriSlit[0] + "  -  " + FriSlit[1];

        String SatText;

        if (SatSlit[0].equals("Open 24 Hrs"))
            SatText = "Open 24 Hours";
        else if (SatSlit[0].equals("Closed"))
            SatText = "Closed";
        else
            SatText = SatSlit[0] + "  -  " + SatSlit[1];


        return "SUN\t\t" + ":" + "\t\t" + SunText + "\n" +
                "MON\t\t" + ":" + "\t\t" + MonText + "\n" +
                "TUE\t\t\t" + ":" + "\t\t" + TueText + "\n" +
                "WED\t\t" + ":" + "\t\t" + WedText + "\n" +
                "THU\t\t\t" + ":" + "\t\t" + ThuText + "\n" +
                "FRI\t\t\t" + ":" + "\t\t" + FriText + "\n" +
                "SAt\t\t\t" + ":" + "\t\t" + SatText + "\n";
    }


    private Chip getChip(final ChipGroup entryChipGroup, String text) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.chip_withoutclosebutton));

        chip.setBackgroundColor(Color.parseColor("#F3F3F3"));
        chip.setText(text);
        chip.setTextSize(10);
        chip.setTextColor(Color.WHITE);
        return chip;
    }
}
