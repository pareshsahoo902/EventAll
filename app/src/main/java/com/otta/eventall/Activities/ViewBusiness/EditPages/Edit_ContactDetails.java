package com.otta.eventall.Activities.ViewBusiness.EditPages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.ContactPersonUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.LandlineUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.MobileNoUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.TitleUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_ContactDetails extends AppCompatActivity {

    CardView SaveButton;
    EditText Edit_ContactPerson, Edit_MobileNumber, Edit_AlternateNo;
    String _BusinessID;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_details);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

        SaveButton = findViewById(R.id.SaveButton);
        Edit_ContactPerson = findViewById(R.id.Edit_ContactPerson);
        Edit_MobileNumber = findViewById(R.id.Edit_MobileNumber);
        Edit_AlternateNo = findViewById(R.id.Edit_AlternateNumber);

        try {
            Edit_ContactPerson.setText(bundle.getString("INFO"));
            Edit_MobileNumber.setText(bundle.getString("INFO2"));
            if (bundle.getString("INFO3").equals("0000000000")) {
            } else {
                Edit_AlternateNo.setText(bundle.getString("INFO3"));
            }
        } catch (Exception e) {
        }

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Edit_ContactPerson.getText().length() > 0 && Edit_MobileNumber.getText().length() > 9) {
                    if (isNewDataIsSameAsOld(bundle.getString("INFO"), Edit_ContactPerson.getText().toString())) {
                        if (isNewDataIsSameAsOld(bundle.getString("INFO2"), Edit_MobileNumber.getText().toString())) {
                            if (Edit_AlternateNo.getText().length() > 0) {
                                if (isNewDataIsSameAsOld(bundle.getString("INFO3"), Edit_AlternateNo.getText().toString()))
                                    finish();
                                else
                                    UpdateAlternateNumber();
                            } else {
                                if (isNewDataIsSameAsOld(bundle.getString("INFO3"), "0000000000"))
                                    finish();
                                else
                                    UpdateAlternateNumber();
                            }
                        } else
                            UpdateMobileNumber();
                    } else
                        UpdateContactPerson();
                } else
                    Toast.makeText(getApplicationContext(), "Please give Contact Name and Mobile number", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void UpdateContactPerson() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_ContactDetails.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContactPersonUpdate_REQUEST request = new ContactPersonUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        request.setContactPerson(Edit_ContactPerson.getText().toString());

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_ContactPerson("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    if (isNewDataIsSameAsOld(bundle.getString("INFO2"), Edit_MobileNumber.getText().toString())) {

                        if (isNewDataIsSameAsOld(bundle.getString("INFO3"), Edit_AlternateNo.getText().toString())) {
                            finish();

                        } else {

                            UpdateAlternateNumber();
                        }

                    } else {
                        UpdateMobileNumber();
                    }

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update Contact Name. Please try again", Snackbar.LENGTH_LONG);
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
                        UpdateContactPerson();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }

    void UpdateMobileNumber() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_ContactDetails.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MobileNoUpdate_REQUEST request = new MobileNoUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        request.setMobileNo(Edit_MobileNumber.getText().toString());

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_MobileNumber("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    if (isNewDataIsSameAsOld(bundle.getString("INFO3"), Edit_AlternateNo.getText().toString())) {
                        finish();
                    } else {
                        UpdateAlternateNumber();
                    }

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update mobile number. Please try again", Snackbar.LENGTH_LONG);
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
                        UpdateMobileNumber();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }

    void UpdateAlternateNumber() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_ContactDetails.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LandlineUpdate_REQUEST request = new LandlineUpdate_REQUEST();
        request.setBusinessId(_BusinessID);

        if (Edit_AlternateNo.getText().length() > 0)
            request.setLandLineNumber(Edit_AlternateNo.getText().toString());
        else
            request.setLandLineNumber("0000000000");


        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_LandlineNumber("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    Toast.makeText(getApplicationContext(), "Contact Details Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update alternate number. Please try again", Snackbar.LENGTH_LONG);
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
                        UpdateAlternateNumber();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }

    boolean isNewDataIsSameAsOld(String old, String New) {

        if (New.equals(old))
            return true;
        else
            return false;
    }
}
