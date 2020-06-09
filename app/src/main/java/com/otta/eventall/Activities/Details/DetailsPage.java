package com.otta.eventall.Activities.Details;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.otta.eventall.Activities.Details.Adapter.DetailsPhotos_Adapter;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.MarketingUtil;

import java.util.ArrayList;
import java.util.Locale;

public class DetailsPage extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    ImageView MapClickPanel;
    ImageView Back;

    RecyclerView PhotosRecyclerView;
    ImageView PhotosSepartor;
    TextView Text_Photos;
    DetailsPhotos_Adapter adapter;

    ImageView Call, Direction, Message, Share;


    ImageView BusinessBannerImage;
    FrameLayout EmptyPhotoFRame;

    LottieAnimationView MapMarker;
    FrameLayout EmptyMapFRame;

    TextView B_Title, B_Location_Small, B_Location_Full, B_Timings, B_ContactPerson, B_ContactDetails, B_Desc, B_AlsoListedIn;
    TextView B_Sun, B_Mon, B_Tue, B_Wed, B_Thu, B_Fri, B_Sat;

    BusinessModel selectedBusiness;

    LinearLayout TimingPanel;
    FrameLayout Liked , Disliked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Bundle bundle = getIntent().getExtras();
        selectedBusiness = (BusinessModel) bundle.getSerializable("BMODEL");


        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();

        LoadBusinessDetails();
    }


    void Init() {


        SupportMapFragment supportmapfragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportmapfragment.getMapAsync(this);

        MapMarker = findViewById(R.id.MapMarker);
        EmptyMapFRame = findViewById(R.id.EmptryFRameMap);
        MapClickPanel = findViewById(R.id.MapClickPanel);

        BusinessBannerImage = findViewById(R.id.BusinessBannerImage);
        EmptyPhotoFRame = findViewById(R.id.NoPhotoFrame);
        Back = findViewById(R.id.Image_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Liked = findViewById(R.id.Liked);
        Disliked = findViewById(R.id.NotLiked);

        B_Title = findViewById(R.id.B_Title);
        B_Location_Small = findViewById(R.id.B_Location_Small);
        B_Location_Full = findViewById(R.id.B_Location_Full);
        B_Timings = findViewById(R.id.B_Timings);
        B_ContactDetails = findViewById(R.id.B_ContactDetails);
        B_ContactPerson = findViewById(R.id.B_ContactPerson);
        B_Desc = findViewById(R.id.B_Desc);
        B_AlsoListedIn = findViewById(R.id.B_AlsoListedIN);
        TimingPanel = findViewById(R.id.Timing_Panel);

        Call = findViewById(R.id.S_Call);
        Direction = findViewById(R.id.S_Direction);
        Message = findViewById(R.id.S_Message);
        Share = findViewById(R.id.S_Share);

        B_Sun = findViewById(R.id.B_Sun);
        B_Mon = findViewById(R.id.B_Mon);
        B_Tue = findViewById(R.id.B_Tue);
        B_Wed = findViewById(R.id.B_Wed);
        B_Thu = findViewById(R.id.B_Thu);
        B_Fri = findViewById(R.id.B_Fri);
        B_Sat = findViewById(R.id.B_Sat);

        PhotosRecyclerView = findViewById(R.id.Recyclerview_Photos);
        PhotosRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        Text_Photos = findViewById(R.id.Text_Photos);
        PhotosSepartor = findViewById(R.id.PhotosSeparator);
    }



    void LoadBusinessDetails() {

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MarketingUtil.Call(getApplicationContext() , selectedBusiness.getMobileNumber());
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketingUtil.Share(DetailsPage.this , selectedBusiness.getBusinessTitle() ,
                        selectedBusiness.getArea() + ", " + selectedBusiness.getCity());
            }
        });

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello " + selectedBusiness.getCotactPersonName());
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" +selectedBusiness.getMobileNumber()) + "@s.whatsapp.net");

                    sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(sendIntent);

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),
                            "WhatsApp cannot be opened", Toast.LENGTH_SHORT).show();
                }

                //MarketingUtil.OpenSMS(DetailsPage.this , selectedBusiness.getMobileNumber() , selectedBusiness.getCotactPersonName());
            }
        });

        Direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBusiness.getCoordinates().equals("0.00,0.00")) {
                    Toast.makeText(getApplicationContext(), "No direction given for this business", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String[] splittedCorrdinates = selectedBusiness.getCoordinates().split(",");

                        String strUri = "http://maps.google.com/maps?q=loc:" +  splittedCorrdinates[0] + "," + splittedCorrdinates[1] + " (" + selectedBusiness.getBusinessTitle() + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }catch (Exception e){

                        Toast.makeText(getApplicationContext() ,"Unable to show direction" , Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        MapClickPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedBusiness.getCoordinates().equals("0.00,0.00")) {
                    Toast.makeText(getApplicationContext(), "No direction given for this business", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String[] splittedCorrdinates = selectedBusiness.getCoordinates().split(",");

                        String strUri = "http://maps.google.com/maps?q=loc:" +  splittedCorrdinates[0] + "," + splittedCorrdinates[1] + " (" + selectedBusiness.getBusinessTitle() + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }catch (Exception e){

                        Toast.makeText(getApplicationContext() ,"Unable to show direction" , Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        RequestOptions op = new RequestOptions()
                .error(R.drawable.image_placeholder_withoutlogo_small)
                .placeholder(R.drawable.image_placeholder_withoutlogo_small);

        if (selectedBusiness.getPhotos() == null) {
            PhotosRecyclerView.setVisibility(View.GONE);
            PhotosSepartor.setVisibility(View.GONE);
            Text_Photos.setVisibility(View.GONE);
            EmptyPhotoFRame.setVisibility(View.VISIBLE);
        } else {

            selectedBusiness.getPhotos().replace("}}e","");
            String[] Splited = selectedBusiness.getPhotos().split(",");
            EmptyPhotoFRame.setVisibility(View.GONE);

            Glide.with(getApplicationContext())
                    .load(Splited[0])
                    .apply(op)
                    .into(BusinessBannerImage);

            //Set Photos Adapter

            ArrayList<String> TempArray = new ArrayList<>();
            for(int i =0 ; i<Splited.length ; i++){
                TempArray.add(Splited[i]);
            }
            adapter = new DetailsPhotos_Adapter(getApplicationContext(), TempArray);
            PhotosRecyclerView.setAdapter(adapter);

        }

        B_Title.setText(selectedBusiness.getBusinessTitle());
        B_Location_Small.setText(selectedBusiness.getArea() + ", " + selectedBusiness.getCity());
        B_Location_Full.setText(selectedBusiness.getBuildingName() + ", " +
                selectedBusiness.getStreet() + ", " +
                //selectedBusiness.getLandmark() + ", " +
                selectedBusiness.getArea() + ", " +
                selectedBusiness.getCity() + ", " +
                //selectedBusiness.getDist() + ", " +
                selectedBusiness.getState());


        if (selectedBusiness.getCoordinates().equals("0.00,0.00")) {
            EmptyMapFRame.setVisibility(View.VISIBLE);
            MapMarker.setVisibility(View.GONE);
            MapClickPanel.setVisibility(View.GONE);

        } else {
            EmptyMapFRame.setVisibility(View.GONE);
            MapMarker.setVisibility(View.VISIBLE);
            MapClickPanel.setVisibility(View.VISIBLE);
        }


        if (selectedBusiness.getOpen24Hr() == 1) {
            B_Timings.setText("Opens Everyday 24 Hours");
            B_Timings.setVisibility(View.VISIBLE);
            TimingPanel.setVisibility(View.GONE);
        } else {
            B_Timings.setVisibility(View.GONE);
            TimingPanel.setVisibility(View.VISIBLE);

            //ChangeColor Acrodding to Timing

            B_Sun.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 1));
            B_Mon.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 2));
            B_Tue.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 3));
            B_Wed.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 4));
            B_Thu.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 5));
            B_Fri.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 6));
            B_Sat.setText(":     " + GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 7));

            ChangeTimingColor();

        }

        B_ContactPerson.setText(selectedBusiness.getCotactPersonName());
        if (selectedBusiness.getLandLineNumber() != null)
            B_ContactDetails.setText(selectedBusiness.getMobileNumber() + ", " + selectedBusiness.getLandLineNumber());
        else
            B_ContactDetails.setText(selectedBusiness.getMobileNumber());

        B_Desc.setText(selectedBusiness.getDesc());


        String[] ListedInArray = selectedBusiness.getSubCategory().split("@");
        String ListedTextString = "";
        for (int i = 0; i < ListedInArray.length; i++) {
            ListedTextString += ListedInArray[i] + "\n";
        }

        B_AlsoListedIn.setText(ListedTextString);

    }

    void ChangeTimingColor() {

        String GREEN_COLOR = "#1CC188";
        String RED_COLOR = "#F36363";
        String GREY_COLOR = "#858585";

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 1).equals("Open 24 Hours")) {
            B_Sun.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 1).equals("Closed")) {
            B_Sun.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Sun.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 2).equals("Open 24 Hours")) {
            B_Mon.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 2).equals("Closed")) {
            B_Mon.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Mon.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 3).equals("Open 24 Hours")) {
            B_Tue.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 3).equals("Closed")) {
            B_Tue.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Tue.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 4).equals("Open 24 Hours")) {
            B_Wed.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 4).equals("Closed")) {
            B_Wed.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Wed.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 5).equals("Open 24 Hours")) {
            B_Thu.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 5).equals("Closed")) {
            B_Thu.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Thu.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 6).equals("Open 24 Hours")) {
            B_Fri.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 6).equals("Closed")) {
            B_Fri.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Fri.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
        }

        if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 7).equals("Open 24 Hours")) {
            B_Sat.setTextColor(Color.parseColor(GREEN_COLOR)); // GREEN
        } else if (GetFormattedTimingSingle(selectedBusiness.getOpenHours(), 7).equals("Closed")) {
            B_Sat.setTextColor(Color.parseColor(RED_COLOR)); // RED
        } else {
            B_Sat.setTextColor(Color.parseColor(GREY_COLOR)); //GREY
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


    private String GetFormattedTimingSingle(String TimingString, int DayIndex) {

        try {
            String timing;
            String[] Spilted = TimingString.split("/");

            String[] SunSplit = Spilted[0].split(",");
            String[] MonSlit = Spilted[1].split(",");
            String[] TueSlit = Spilted[2].split(",");
            String[] WedSlit = Spilted[3].split(",");
            String[] ThuSlit = Spilted[4].split(",");
            String[] FriSlit = Spilted[5].split(",");
            String[] SatSlit = Spilted[6].split(",");

            switch (DayIndex) {
                case 1:

                    String SunText;
                    if (SunSplit[0].equals("Open 24 Hrs"))
                        SunText = "Open 24 Hours";
                    else if (SunSplit[0].equals("Closed"))
                        SunText = "Closed";
                    else
                        SunText = SunSplit[0] + "  -  " + SunSplit[1];
                    return SunText;

                case 2:

                    String MonText;
                    if (MonSlit[0].equals("Open 24 Hrs"))
                        MonText = "Open 24 Hours";
                    else if (MonSlit[0].equals("Closed"))
                        MonText = "Closed";
                    else
                        MonText = MonSlit[0] + "  -  " + MonSlit[1];
                    return MonText;

                case 3:

                    String TueText;
                    if (TueSlit[0].equals("Open 24 Hrs"))
                        TueText = "Open 24 Hours";
                    else if (TueSlit[0].equals("Closed"))
                        TueText = "Closed";
                    else
                        TueText = TueSlit[0] + "  -  " + TueSlit[1];
                    return TueText;

                case 4:

                    String WedText;
                    if (WedSlit[0].equals("Open 24 Hrs"))
                        WedText = "Open 24 Hours";
                    else if (WedSlit[0].equals("Closed"))
                        WedText = "Closed";
                    else
                        WedText = WedSlit[0] + "  -  " + WedSlit[1];
                    return WedText;

                case 5:

                    String ThuText;
                    if (ThuSlit[0].equals("Open 24 Hrs"))
                        ThuText = "Open 24 Hours";
                    else if (ThuSlit[0].equals("Closed"))
                        ThuText = "Closed";
                    else
                        ThuText = ThuSlit[0] + "  -  " + ThuSlit[1];
                    return ThuText;

                case 6:

                    String FriText;
                    if (FriSlit[0].equals("Open 24 Hrs"))
                        FriText = "Open 24 Hours";
                    else if (FriSlit[0].equals("Closed"))
                        FriText = "Closed";
                    else
                        FriText = FriSlit[0] + "  -  " + FriSlit[1];
                    return FriText;

                case 7:

                    String SatText;
                    if (SatSlit[0].equals("Open 24 Hrs"))
                        SatText = "Open 24 Hours";
                    else if (SatSlit[0].equals("Closed"))
                        SatText = "Closed";
                    else
                        SatText = SatSlit[0] + "  -  " + SatSlit[1];
                    return SatText;

                default:
                    return "";

            }
        } catch (Exception e) {
            return "No Timing Available";
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);

        map.getUiSettings().setAllGesturesEnabled(false);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        if (selectedBusiness.getCoordinates().equals("0.00,0.00") == false) {

            String PickedLocation = selectedBusiness.getCoordinates();
            String[] SpiledtedAltLong = PickedLocation.split(",");

            LatLng latLng = new LatLng(Double.parseDouble(SpiledtedAltLong[0]), Double.parseDouble(SpiledtedAltLong[1]));
            map.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)));
        }
    }
}
