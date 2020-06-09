package com.otta.eventall.Activities.AddBusiness;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

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

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Business_timings extends AppCompatActivity {

    Spinner SunSpinnerOne, SunSpinnerTwo;
    Spinner MonSpinnerOne, MonSpinnerTwo;
    Spinner TueSpinnerOne, TueSpinnerTwo;
    Spinner WedSpinnerOne, WedSpinnerTwo;
    Spinner ThuSpinnerOne, ThuSpinnerTwo;
    Spinner FriSpinnerOne, FriSpinnerTwo;
    Spinner SatSpinnerOne, SatSpinnerTwo;

    CheckBox Open24Hrs, ApplyToAll;

    CardView SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_timings);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Clickers();

        ShowHide_ApplyToAllButton();

    }

    void Init() {

        SunSpinnerOne = findViewById(R.id.SunSpinnerOne);
        SunSpinnerTwo = findViewById(R.id.SunSpinnerTwo);

        MonSpinnerOne = findViewById(R.id.MonSpinnerOne);
        MonSpinnerTwo = findViewById(R.id.MonSpinnerTwo);

        TueSpinnerOne = findViewById(R.id.TueSpinnerOne);
        TueSpinnerTwo = findViewById(R.id.TueSpinnerTwo);

        WedSpinnerOne = findViewById(R.id.WedSpinnerOne);
        WedSpinnerTwo = findViewById(R.id.WedSpinnerTwo);

        ThuSpinnerOne = findViewById(R.id.ThuSpinnerOne);
        ThuSpinnerTwo = findViewById(R.id.ThuSpinnerTwo);

        FriSpinnerOne = findViewById(R.id.FriSpinnerOne);
        FriSpinnerTwo = findViewById(R.id.FriSpinnerTwo);

        SatSpinnerOne = findViewById(R.id.SatSpinnerOne);
        SatSpinnerTwo = findViewById(R.id.SatSpinnerTwo);

        ApplyToAll = findViewById(R.id.applytoall);
        Open24Hrs = findViewById(R.id.Opens247);

        ApplyToAll.setVisibility(View.GONE);


        SaveButton = findViewById(R.id.SaveButton);
    }

    void Clickers() {
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateFields())
                    SaveDetailsInRequest();
                else
                    Toast.makeText(getApplicationContext(), "Please give timing for every day", Toast.LENGTH_SHORT).show();
            }
        });

        ApplyToAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    MonSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    MonSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);

                    TueSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    TueSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);

                    WedSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    WedSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);

                    ThuSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    ThuSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);

                    FriSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    FriSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);

                    SatSpinnerOne.setSelection(SunSpinnerOne.getSelectedItemPosition(), true);
                    SatSpinnerTwo.setSelection(SunSpinnerTwo.getSelectedItemPosition(), true);
                } else {

                }
            }
        });

        Open24Hrs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {


                    SunSpinnerOne.setSelection(1);
                    SunSpinnerTwo.setSelection(1);

                    MonSpinnerOne.setSelection(1);
                    MonSpinnerTwo.setSelection(1);

                    TueSpinnerOne.setSelection(1);
                    TueSpinnerTwo.setSelection(1);

                    WedSpinnerOne.setSelection(1);
                    WedSpinnerTwo.setSelection(1);

                    ThuSpinnerOne.setSelection(1);
                    ThuSpinnerTwo.setSelection(1);

                    FriSpinnerOne.setSelection(1);
                    FriSpinnerTwo.setSelection(1);

                    SatSpinnerOne.setSelection(1);
                    SatSpinnerTwo.setSelection(1);


                    SunSpinnerOne.setEnabled(false);
                    SunSpinnerTwo.setEnabled(false);

                    MonSpinnerOne.setEnabled(false);
                    MonSpinnerTwo.setEnabled(false);

                    TueSpinnerOne.setEnabled(false);
                    TueSpinnerTwo.setEnabled(false);

                    WedSpinnerOne.setEnabled(false);
                    WedSpinnerTwo.setEnabled(false);

                    ThuSpinnerOne.setEnabled(false);
                    ThuSpinnerTwo.setEnabled(false);

                    FriSpinnerOne.setEnabled(false);
                    FriSpinnerTwo.setEnabled(false);

                    SatSpinnerOne.setEnabled(false);
                    SatSpinnerTwo.setEnabled(false);

                    ApplyToAll.setVisibility(View.GONE);

                } else {

                    if (SunSpinnerOne.getSelectedItem().equals("Select") || SunSpinnerTwo.getSelectedItem().equals("Select")) {
                        ApplyToAll.setVisibility(View.GONE);
                    } else {
                        ApplyToAll.setVisibility(View.VISIBLE);
                    }


                    SunSpinnerOne.setSelection(0);
                    SunSpinnerTwo.setSelection(0);

                    MonSpinnerOne.setSelection(0);
                    MonSpinnerTwo.setSelection(0);

                    TueSpinnerOne.setSelection(0);
                    TueSpinnerTwo.setSelection(0);

                    WedSpinnerOne.setSelection(0);
                    WedSpinnerTwo.setSelection(0);

                    ThuSpinnerOne.setSelection(0);
                    ThuSpinnerTwo.setSelection(0);

                    FriSpinnerOne.setSelection(0);
                    FriSpinnerTwo.setSelection(0);

                    SatSpinnerOne.setSelection(0);
                    SatSpinnerTwo.setSelection(0);


                    SunSpinnerOne.setEnabled(true);
                    SunSpinnerTwo.setEnabled(true);

                    MonSpinnerOne.setEnabled(true);
                    MonSpinnerTwo.setEnabled(true);

                    TueSpinnerOne.setEnabled(true);
                    TueSpinnerTwo.setEnabled(true);

                    WedSpinnerOne.setEnabled(true);
                    WedSpinnerTwo.setEnabled(true);

                    ThuSpinnerOne.setEnabled(true);
                    ThuSpinnerTwo.setEnabled(true);

                    FriSpinnerOne.setEnabled(true);
                    FriSpinnerTwo.setEnabled(true);

                    SatSpinnerOne.setEnabled(true);
                    SatSpinnerTwo.setEnabled(true);

                }
            }
        });
    }

    void ShowHide_ApplyToAllButton() {

        SunSpinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Open24Hrs.isChecked() == false) {

                    if (SunSpinnerOne.getSelectedItem().equals("Select")) {
                        ApplyToAll.setVisibility(View.GONE);
                    } else {
                        if (SunSpinnerTwo.getSelectedItem().equals("Select") == true) {
                            ApplyToAll.setVisibility(View.GONE);
                        } else {
                            ApplyToAll.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SunSpinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Open24Hrs.isChecked() == false) {

                    if (SunSpinnerTwo.getSelectedItem().equals("Select")) {
                        ApplyToAll.setVisibility(View.GONE);
                    } else {
                        if (SunSpinnerOne.getSelectedItem().equals("Select") == true) {
                            ApplyToAll.setVisibility(View.GONE);
                        } else {
                            ApplyToAll.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    boolean ValidateFields() {

        if (SunSpinnerOne.getSelectedItem().equals("Select") || SunSpinnerTwo.getSelectedItem().equals("Select") ||
                MonSpinnerOne.getSelectedItem().equals("Select") || MonSpinnerTwo.getSelectedItem().equals("Select") ||
                TueSpinnerOne.getSelectedItem().equals("Select") || TueSpinnerTwo.getSelectedItem().equals("Select") ||
                WedSpinnerOne.getSelectedItem().equals("Select") || WedSpinnerTwo.getSelectedItem().equals("Select") ||
                ThuSpinnerOne.getSelectedItem().equals("Select") || ThuSpinnerTwo.getSelectedItem().equals("Select") ||
                FriSpinnerOne.getSelectedItem().equals("Select") || FriSpinnerTwo.getSelectedItem().equals("Select") ||
                SatSpinnerOne.getSelectedItem().equals("Select") || SatSpinnerTwo.getSelectedItem().equals("Select")) {

            return false;
        } else {
            return true;
        }
    }


    void SaveDetailsInRequest() {

        if (Open24Hrs.isChecked())
            ArrayPlace.addNewBusiness_request.setOpen24Hrs(1);
        else
            ArrayPlace.addNewBusiness_request.setOpen24Hrs(0);

        ArrayPlace.addNewBusiness_request.setOpenHrs(GenerateTimingString());

        CreateBusiness();


    }


    String GenerateTiminginJSON() {

        JSONObject TimingJSON = new JSONObject();

        try {

            JSONObject SundayOpenClose = new JSONObject();
            SundayOpenClose.put("opning_time", SunSpinnerOne.getSelectedItem().toString());
            SundayOpenClose.put("closing_time", SunSpinnerTwo.getSelectedItem().toString());

            JSONObject MondayOpenClose = new JSONObject();
            MondayOpenClose.put("opning_time", MonSpinnerOne.getSelectedItem().toString());
            MondayOpenClose.put("closing_time", MonSpinnerTwo.getSelectedItem().toString());

            JSONObject TuesdayOpenClose = new JSONObject();
            TuesdayOpenClose.put("opning_time", TueSpinnerOne.getSelectedItem().toString());
            TuesdayOpenClose.put("closing_time", TueSpinnerTwo.getSelectedItem().toString());

            JSONObject WednesdayOpenClose = new JSONObject();
            WednesdayOpenClose.put("opning_time", WedSpinnerOne.getSelectedItem().toString());
            WednesdayOpenClose.put("closing_time", WedSpinnerTwo.getSelectedItem().toString());

            JSONObject ThrOpenClose = new JSONObject();
            ThrOpenClose.put("opning_time", ThuSpinnerOne.getSelectedItem().toString());
            ThrOpenClose.put("closing_time", ThuSpinnerTwo.getSelectedItem().toString());

            JSONObject FridayOpenClose = new JSONObject();
            FridayOpenClose.put("opning_time", FriSpinnerOne.getSelectedItem().toString());
            FridayOpenClose.put("closing_time", FriSpinnerTwo.getSelectedItem().toString());

            JSONObject SatOpenClose = new JSONObject();
            SatOpenClose.put("opning_time", SatSpinnerOne.getSelectedItem().toString());
            SatOpenClose.put("closing_time", SatSpinnerTwo.getSelectedItem().toString());

            TimingJSON.put("Sunday", SundayOpenClose);
            TimingJSON.put("Monday", MondayOpenClose);
            TimingJSON.put("Tuesday", TuesdayOpenClose);
            TimingJSON.put("Wednesday", WednesdayOpenClose);
            TimingJSON.put("Thursday", ThrOpenClose);
            TimingJSON.put("Friday", FridayOpenClose);
            TimingJSON.put("Saturday", SatOpenClose);

        } catch (Exception e) {

        }

        return TimingJSON.toString();
    }


    String GenerateTimingString() {

        String TimingString = SunSpinnerOne.getSelectedItem().toString() + "," + SunSpinnerTwo.getSelectedItem().toString() + "/" +
                MonSpinnerOne.getSelectedItem().toString() + "," + MonSpinnerTwo.getSelectedItem().toString() + "/" +
                TueSpinnerOne.getSelectedItem().toString() + "," + TueSpinnerTwo.getSelectedItem().toString() + "/" +
                WedSpinnerOne.getSelectedItem().toString() + "," + WedSpinnerTwo.getSelectedItem().toString() + "/" +
                ThuSpinnerOne.getSelectedItem().toString() + "," + ThuSpinnerTwo.getSelectedItem().toString() + "/" +
                FriSpinnerOne.getSelectedItem().toString() + "," + FriSpinnerTwo.getSelectedItem().toString() + "/" +
                SatSpinnerOne.getSelectedItem().toString() + "," + SatSpinnerTwo.getSelectedItem().toString();

        return TimingString;
    }


    void CreateBusiness() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Business_timings.this, "Adding Business");
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
