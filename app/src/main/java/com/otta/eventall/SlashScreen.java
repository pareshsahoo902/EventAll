package com.otta.eventall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.otta.eventall.Activities.Home.Home;
import com.otta.eventall.Activities.Login.Login;
import com.otta.eventall.Activities.ViewBusiness.BusinessList;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.UserDetailsDB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlashScreen extends AppCompatActivity {

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        //Firebase Initialization
        FirebaseApp.initializeApp(getApplicationContext());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        timer = new CountDownTimer(1000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (ConfidentialDB.get_AccessToken(getApplicationContext()) != null) {
                    FetchUserINFO();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        //SetMockCategoriesForTesting();
        FetchAllCategoryList();
    }


    void FetchAllCategoryList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.CATEGORY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<AllCategory_RESPONSE> call = api.getAllCategories();
        call.enqueue(new Callback<AllCategory_RESPONSE>() {
            @Override
            public void onResponse(Call<AllCategory_RESPONSE> call, Response<AllCategory_RESPONSE> response) {

                AllCategory_RESPONSE response1 = response.body();


                try {
                    if (response1 == null) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please restart the app to retry.", Snackbar.LENGTH_LONG);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                                finish();
                                startActivity(intent);
                            }
                        });
                        snackbar.setDuration(100000);
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.show();
                    } else {

                        if (response1.isStatus()) {

                            if (ArrayPlace.AllCategoryList == null) {
                                ArrayPlace.AllCategoryList = new ArrayList<>();
                                ArrayPlace.AllCategoryList.addAll(response1.getCategoryList());
                            } else {
                                ArrayPlace.AllCategoryList.clear();
                                ArrayPlace.AllCategoryList.addAll(response1.getCategoryList());
                            }

                            timer.start();

                        } else {

                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please restart the app to retry.", Snackbar.LENGTH_LONG);
                            snackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            snackbar.setDuration(100000);
                            snackbar.setActionTextColor(Color.WHITE);
                            snackbar.show();

                        }
                    }

                }catch (Exception e){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    snackbar.setDuration(100000);
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AllCategory_RESPONSE> call, Throwable t) {

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please restart the app to retry.", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                        finish();
                        startActivity(intent);
                    }
                });
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.setDuration(100000);
                snackbar.show();

            }
        });
    }


    void FetchUserINFO(){

        final AlertDialog dialog = DialogHelper.getLoadingDialog(SlashScreen.this, "Logging In");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<UserInfo_RESONSE> call = api.getUserInfo("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()));
        call.enqueue(new Callback<UserInfo_RESONSE>() {
            @Override
            public void onResponse(Call<UserInfo_RESONSE> call, Response<UserInfo_RESONSE> response) {

                dialog.dismiss();

                UserInfo_RESONSE resonse = response.body();

                try {
                    if (resonse.isStatus()) {

                        UserDetailsDB.save_UserName(getApplicationContext(), resonse.getUserInfo().getName());
                        UserDetailsDB.save_ProfilePictureURL(getApplicationContext(), resonse.getUserInfo().getPhotoURL());
                        UserDetailsDB.save_UserEmailAddress(getApplicationContext() , resonse.getUserInfo().getEmail());

                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();

                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to login.Please try again", Snackbar.LENGTH_LONG);
                        snackbar.setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                                finish();
                                startActivity(intent);
                            }
                        });
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.setDuration(100000);
                        snackbar.show();
                    }

                }catch (Exception e){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to login.Please try again", Snackbar.LENGTH_LONG);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.setDuration(100000);
                    snackbar.show();
                }


            }

            @Override
            public void onFailure(Call<UserInfo_RESONSE> call, Throwable t) {

                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please restart the app to retry.", Snackbar.LENGTH_LONG);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SlashScreen.class);
                        finish();
                        startActivity(intent);
                    }
                });
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.setDuration(100000);
                snackbar.show();

            }
        });
    }

}
