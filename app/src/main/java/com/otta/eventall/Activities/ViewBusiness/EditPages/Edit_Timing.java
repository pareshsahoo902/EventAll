package com.otta.eventall.Activities.ViewBusiness.EditPages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.ContactPersonUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.Open24hrUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.OpenHoursUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
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

public class Edit_Timing extends AppCompatActivity {

    Spinner SunSpinnerOne, SunSpinnerTwo;
    Spinner MonSpinnerOne, MonSpinnerTwo;
    Spinner TueSpinnerOne, TueSpinnerTwo;
    Spinner WedSpinnerOne, WedSpinnerTwo;
    Spinner ThuSpinnerOne, ThuSpinnerTwo;
    Spinner FriSpinnerOne, FriSpinnerTwo;
    Spinner SatSpinnerOne, SatSpinnerTwo;

    CheckBox Open24Hrs, ApplyToAll;

    CardView SaveButton;
    ImageView Back;
    String _BusinessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timing);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

        Init();
        Clickers();

        if(bundle.getInt("is24x7") == 1)
            Open24Hrs.setChecked(true);

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

        Back = findViewById(R.id.backbala);
        SaveButton = findViewById(R.id.SaveButton);
    }

    void Clickers() {

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateFields())
                    UpdateOpen24hr();
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


    void UpdateOpen24hr() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_Timing.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Open24hrUpdate_REQUEST request = new Open24hrUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        if (Open24Hrs.isChecked())
            request.setOpen24Hr(1);
        else
            request.setOpen24Hr(0);

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_Open24Hr("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    UpdateOpenHours();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update 24 x 7. Please try again", Snackbar.LENGTH_LONG);
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
                        UpdateOpen24hr();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }

    void UpdateOpenHours() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_Timing.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenHoursUpdate_REQUEST request = new OpenHoursUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        request.setOpenHours(GenerateTimingString());

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_OpenHours("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    Toast.makeText(getApplicationContext(), "Business Timing Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update timing. Please try again", Snackbar.LENGTH_LONG);
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
                        UpdateOpenHours();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }

}
