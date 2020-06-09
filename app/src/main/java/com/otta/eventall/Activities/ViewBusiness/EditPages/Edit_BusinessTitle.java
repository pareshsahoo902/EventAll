package com.otta.eventall.Activities.ViewBusiness.EditPages;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.DescUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.TitleUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.OttaDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_BusinessTitle extends AppCompatActivity {

    CardView SaveButton;
    EditText TagLine;
    String _BusinessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_title);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

        SaveButton = findViewById(R.id.SaveButton);
        TagLine = findViewById(R.id.TagLineEdit);

        TagLine.setText(bundle.getString("INFO"));


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TagLine.getText().length() > 0) {
                    if (isNewDataIsSameAsOld(bundle.getString("INFO"), TagLine.getText().toString()))
                        finish(); // Same Data . NO need to update
                    else
                        Update();

                } else {
                    Toast.makeText(getApplicationContext(), "Please give Title of your Business", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void Update(){

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_BusinessTitle.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TitleUpdate_REQUEST re = new TitleUpdate_REQUEST();
        re.setBusinessID(_BusinessID);
        re.setTitle(TagLine.getText().toString());


        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_Title("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()) , re);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if(response1.isStatus()){
                    Toast.makeText(getApplicationContext() , "Title Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to update. Please try again", Snackbar.LENGTH_LONG);
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

    boolean isNewDataIsSameAsOld(String old, String New) {

        if (New.equals(old))
            return true;
        else
            return false;
    }
}
