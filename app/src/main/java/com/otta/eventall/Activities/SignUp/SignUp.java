package com.otta.eventall.Activities.SignUp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.Login.Login;
import com.otta.eventall.Activities.SignUp.Methods.SignUp_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.SignUp_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class SignUp extends AppCompatActivity {


    int isshowingpassword = 0;
    int isshowingConfirmpassword = 0;
    FrameLayout AlreadyHaveAnAccount;
    CardView SignUpButton;
    EditText Email, Password, ConfirmPassword;
    ImageView SeePassword, SeeConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Cickers();

    }


    void Init() {
        AlreadyHaveAnAccount = findViewById(R.id.Frame_AlreadyHaveAnAccount);
        SignUpButton = findViewById(R.id.Card_SignUp);
        Email = findViewById(R.id.Edit_Email);
        Password = findViewById(R.id.Edit_Password);
        ConfirmPassword = findViewById(R.id.Edit_ConfirmPassword);
        SeePassword = findViewById(R.id.seepassword);
        SeeConfirmPassword = findViewById(R.id.seeconfirmpassword);
    }

    void Cickers() {

        SeePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (isshowingpassword) {
                    case 0:
                        Password.setInputType(
                                InputType.TYPE_TEXT_VARIATION_NORMAL);
                        Password.setSelection(Password.getText().length());
                        isshowingpassword = 1;
                        break;

                    case 1:
                        Password.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Password.setSelection(Password.getText().length());
                        isshowingpassword = 0;
                        break;

                }
            }
        });


        SeeConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (isshowingConfirmpassword) {
                    case 0:
                        ConfirmPassword.setInputType(
                                InputType.TYPE_TEXT_VARIATION_NORMAL);
                        ConfirmPassword.setSelection(ConfirmPassword.getText().length());
                        isshowingConfirmpassword = 1;
                        break;

                    case 1:
                        ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        ConfirmPassword.setSelection(ConfirmPassword.getText().length());
                        isshowingConfirmpassword = 0;
                        break;
                }
            }
        });


        PushDownAnim.setPushDownAnimTo(AlreadyHaveAnAccount, SignUpButton)
                .setScale(MODE_SCALE,
                        PushDownAnim.DEFAULT_PUSH_SCALE)

                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (v.getId()) {

                            case R.id.Frame_AlreadyHaveAnAccount:
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                                break;


                            case R.id.Card_SignUp:

                                if (ValidateInputs()) {
                                    if (ValidateEmail()) {
                                        if (ConfirmPassword())

                                            //TODO : SIGN UP PROCESS STARTS HERE
                                            SignUp(Email.getText().toString(), ConfirmPassword.getText().toString());

                                        else
                                            Toast.makeText(getApplicationContext(), "Confirm Password do not match", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getApplicationContext(), "Please enter a valid Email Address", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please put all three fields", Toast.LENGTH_SHORT).show();
                                }

                        }

                    }
                });
    }

    boolean ValidateInputs() {

        if (Email.getText().length() > 0 && Password.getText().length() > 0 && ConfirmPassword.getText().length() > 0)
            return true;
        else
            return false;

    }

    boolean ConfirmPassword() {
        if (ConfirmPassword.getText().toString().toLowerCase().equals(Password.getText().toString().toLowerCase()))
            return true;
        else
            return false;
    }

    boolean ValidateEmail() {
        String email = Email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern))
            return true;
        else
            return false;
    }


    //Server Stuff ----------------------------------------------

    void SignUp(String _Email, String _Password) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(SignUp.this, "Signing Up");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SignUp_REQUEST request = new SignUp_REQUEST(_Email, _Password);

        API api = retrofit.create(API.class);
        Call<SignUp_RESPONSE> call = api.SignUp(request);
        call.enqueue(new Callback<SignUp_RESPONSE>() {
            @Override
            public void onResponse(Call<SignUp_RESPONSE> call, Response<SignUp_RESPONSE> response) {

                dialog.dismiss();
                SignUp_RESPONSE response1 = response.body();

                if (response1.getStatus()) {

                    //Save Token
                    ConfidentialDB.save_AccessToken(getApplicationContext(), response1.getToken());
                    ConfidentialDB.save_Email(getApplicationContext(), _Email);
                    ConfidentialDB.save_Password(getApplicationContext(), _Password);
                    ConfidentialDB.save_logStatus(getApplicationContext(), true);

                    //Open User Details Page
                    Intent OpenUser_Details = new Intent(getApplicationContext(), User_Details.class);
                    startActivity(OpenUser_Details);
                    finish();

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "We already have an account on this email address. Please login.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent OpenLoginPage = new Intent(getApplicationContext(), Login.class);
                            startActivity(OpenLoginPage);
                            finish();
                        }
                    });
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<SignUp_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect with Server. Please check you Internet Access.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}



