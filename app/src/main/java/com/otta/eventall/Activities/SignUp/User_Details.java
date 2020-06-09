package com.otta.eventall.Activities.SignUp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.AddBusiness.Model.OwnerModel;
import com.otta.eventall.Activities.SignUp.Methods.UserDetails_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.UserDetails_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.OttaDB;
import com.otta.eventall.Utils.UserDetailsDB;
import com.thekhaeng.pushdownanim.PushDownAnim;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class User_Details  extends AppCompatActivity {

    FrameLayout Male, Female, Other;
    EditText Name, Number;
    int Gender = -1;
    CardView SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Clicker();
    }

    void Init() {
        Male = findViewById(R.id.maleframe);
        Female = findViewById(R.id.femaleframe);
        Other = findViewById(R.id.otherframe);
        Name = findViewById(R.id.Name);
        Number = findViewById(R.id.Number);
        SaveButton = findViewById(R.id.SaveButton);
    }


    void Clicker() {


        PushDownAnim.setPushDownAnimTo(SaveButton)
                .setScale(MODE_SCALE,
                        PushDownAnim.DEFAULT_PUSH_SCALE)

                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ValidateFields()) {

                            //Save Locally
                            UserDetailsDB.save_UserName(getApplicationContext() , Name.getText().toString());

                            //Save at Server
                            SaveUserInfo();

                        } else {

                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please fill up every field", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });


        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Male.setBackgroundColor(Color.parseColor("#20000000"));
                Female.setBackgroundColor(Color.TRANSPARENT);
                Other.setBackgroundColor(Color.TRANSPARENT);
                Gender = 1;
            }
        });

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Female.setBackgroundColor(Color.parseColor("#20000000"));
                Male.setBackgroundColor(Color.TRANSPARENT);
                Other.setBackgroundColor(Color.TRANSPARENT);
                Gender = 2;
            }
        });

        Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Other.setBackgroundColor(Color.parseColor("#20000000"));
                Female.setBackgroundColor(Color.TRANSPARENT);
                Male.setBackgroundColor(Color.TRANSPARENT);
                Gender = 3;
            }
        });
    }


    boolean ValidateFields() {
        if (Name.getText().length() > 0 &&
                Number.getText().length() > 9 &&
                Gender != -1) {
            return true;
        } else
            return false;
    }


    //Server Stuff ----------------------------------------------

    void SaveUserInfo() {

        String NameString = Name.getText().toString();
        String NumberString = Number.getText().toString();
        int GenderValue = Gender;

        final AlertDialog dialog = DialogHelper.getLoadingDialog(User_Details.this, "Saving Details");
        dialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserDetails_REQUEST request = new UserDetails_REQUEST();
        request.setGender(GenderValue);
        request.setName(NameString);
        request.setPhoneNumber(NumberString);

        API api = retrofit.create(API.class);
        Call<UserDetails_RESPONSE> call = api.Save_UserDetails("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()) , request);

        call.enqueue(new Callback<UserDetails_RESPONSE>() {
            @Override
            public void onResponse(Call<UserDetails_RESPONSE> call, Response<UserDetails_RESPONSE> response) {
                dialog.dismiss();
                UserDetails_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    Intent intent = new Intent(getApplicationContext(), User_ProfilePicture.class);
                    startActivity(intent);
                    finish();

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to save your details. Please try again.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SaveUserInfo();
                        }
                    });
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.setDuration(100000);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<UserDetails_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect with Server. Please check you Internet Access." + t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

}
