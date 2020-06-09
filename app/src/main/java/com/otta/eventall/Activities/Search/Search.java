package com.otta.eventall.Activities.Search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telecom.StatusHints;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.otta.eventall.Activities.Search.Adapter.Search_Adapter;
import com.otta.eventall.Activities.Search.Methods.Search_REQUEST;
import com.otta.eventall.Activities.Search.Methods.Search_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Location;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.SlashScreen;
import com.otta.eventall.UserInfo_RESONSE;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity {

    TextView Text_Header, Text_Location, Text_ChoosedCategory;
    AlertDialog alertDialog;
    FrameLayout Frame_Category;
    FrameLayout Card_Search, EmptyFrame;
    TextView Text_SearchAnything;
    FrameLayout Frame_Location;

    Search_Adapter adapter;
    RecyclerView recyclerView;

    String ChoosedCategory = null;

    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyDYDfcg8XeGsOuV_urx1Hd1vH38khi5DRI");

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);



        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());
        Text_Header = findViewById(R.id.Text_Header);
        Frame_Category = findViewById(R.id.Frame_category);

        Text_Location = findViewById(R.id.text_location);
        Text_ChoosedCategory = findViewById(R.id.text_categorytosearch);

        Card_Search = findViewById(R.id.Card_Search);
        EmptyFrame = findViewById(R.id.EmptryFrame);
        Text_SearchAnything = findViewById(R.id.Text_SearchAnything);
        Frame_Location = findViewById(R.id.Frame_Location);
        recyclerView = findViewById(R.id.searchRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        EmptyFrame.setVisibility(View.GONE);

        Text_Header.setTypeface(FontEngine.getBoldFont(getApplicationContext()));

        Text_Location.setSelected(true);

        Frame_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryChooserDialog(Search.this);
            }
        });

        Card_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Text_Location.getText().toString().equals("Select Location")) {
                    ShowLocationChooser(Search.this);
                } else if (ChoosedCategory == null) {
                    showCategoryChooserDialog(Search.this);
                } else {
                    Search();
                }

            }
        });

        Frame_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowLocationChooser(Search.this);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10001) {
            if (resultCode == Activity.RESULT_OK) {
                Text_Location.setText(data.getStringExtra("loc"));
            }
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Text_Location.setText(place.getName());

                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("MAP", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void ShowLocationChooser(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_location_type, null);

        EditText address = dialogLayout.findViewById(R.id.Edit_Address);
        CardView AddButton = dialogLayout.findViewById(R.id.CatagoryAddButton);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Text_Location.setText(address.getText().toString());
                alertDialog.dismiss();
            }
        });

        FontEngine.overrideTypeface(getApplicationContext(), dialogLayout);
        builder.setView(dialogLayout);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);

        alertDialog.show();

    }

    public void showCategoryChooserDialog(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_select_catergry, null);

        Spinner CatSpinner = dialogLayout.findViewById(R.id.CatSpinner);
        Spinner SubCatSpinne = dialogLayout.findViewById(R.id.SubCatSpinner);

        CardView SubCatCardView = dialogLayout.findViewById(R.id.SubCatCardView);

        CardView AddButton = dialogLayout.findViewById(R.id.CatagoryAddButton);

        ArrayList<String> CatList = new ArrayList<>();
        ArrayList<String> SubCatList = new ArrayList<>();

        for (int i = 0; i < ArrayPlace.AllCategoryList.size(); i++) {
            CatList.add(ArrayPlace.AllCategoryList.get(i).getCatName());
        }

        ArrayAdapter<String> CatspinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, CatList);
        CatSpinner.setAdapter(CatspinnerArrayAdapter);

        CatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SubCatList.clear();

                for (int k = 0; k < ArrayPlace.AllCategoryList.get(i).getSubCategoryList().size(); k++) {
                    SubCatList.add(ArrayPlace.AllCategoryList.get(i).getSubCategoryList().get(k).getSubCatName());
                }

                if (SubCatList.size() == 0) {
                    SubCatCardView.setVisibility(View.GONE);
                } else {
                    SubCatCardView.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> SubCatspinnerArrayAdapter = new ArrayAdapter<>(
                            activity, android.R.layout.simple_spinner_item, SubCatList);
                    SubCatSpinne.setAdapter(SubCatspinnerArrayAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SubCatList.size() == 0) {
                    Text_ChoosedCategory.setText(CatSpinner.getSelectedItem().toString());
                } else {
                    Text_ChoosedCategory.setText(SubCatSpinne.getSelectedItem().toString());
                }

                ChoosedCategory = Text_ChoosedCategory.getText().toString();

                alertDialog.dismiss();
            }
        });


        FontEngine.overrideTypeface(getApplicationContext(), dialogLayout);
        builder.setView(dialogLayout);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);

        alertDialog.show();
    }


    void Search() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Search.this, "Searching");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.CATEGORY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Search_REQUEST request = new Search_REQUEST();

        request.setSubCategory(ChoosedCategory);
        request.setLocation(Text_Location.getText().toString());

        API api = retrofit.create(API.class);
        Call<Search_RESPONSE> call = api.Search(request);
        call.enqueue(new Callback<Search_RESPONSE>() {
            @Override
            public void onResponse(Call<Search_RESPONSE> call, Response<Search_RESPONSE> response) {

                dialog.dismiss();
                Search_RESPONSE response1 = response.body();


                if (response1.isStatus()) {

                    recyclerView.setVisibility(View.VISIBLE);
                    Text_SearchAnything.setVisibility(View.GONE);
                    EmptyFrame.setVisibility(View.GONE);
                    adapter = new Search_Adapter(Search.this, response1.getResultList());
                    recyclerView.setAdapter(adapter);

                } else {

                    recyclerView.setVisibility(View.GONE);
                    Text_SearchAnything.setVisibility(View.GONE);
                    EmptyFrame.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Search_RESPONSE> call, Throwable t) {

                dialog.dismiss();

                Text_SearchAnything.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "No Internet Access. Please try again", Toast.LENGTH_SHORT).show();
                EmptyFrame.setVisibility(View.VISIBLE);
            }
        });

    }
}
