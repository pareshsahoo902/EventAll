package com.otta.eventall.Activities.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.Home.Home;
import com.otta.eventall.Activities.Login.Methods.Login_REQUEST;
import com.otta.eventall.Activities.Login.Methods.Login_RESPONSE;
import com.otta.eventall.Activities.SignUp.SignUp;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.time.format.TextStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Login extends AppCompatActivity {

    FrameLayout CreateNewAccount;
    CardView LoginButton, SkipCard;
    TextView SkipButton;
    EditText Email, Password;
    ImageView seepassword;
    int isshowingpassword = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Cickers();
    }

    void Init() {
        CreateNewAccount = findViewById(R.id.Frame_CreateNewAccount);
        LoginButton = findViewById(R.id.Card_Login);
        Email = findViewById(R.id.Edit_Email);
        Password = findViewById(R.id.Edit_Password);
        SkipButton = findViewById(R.id.SkipButton);
        SkipCard = findViewById(R.id.Card_SKIP);
        seepassword = findViewById(R.id.seepassword);
    }

    void Cickers() {


        seepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                switch (isshowingpassword){
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




                FontEngine.overrideTypeface(getApplicationContext() , Password);

            }
        });

        PushDownAnim.setPushDownAnimTo(CreateNewAccount, LoginButton, SkipButton, SkipCard)
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

                            case R.id.Card_SKIP:
                                Intent Skip = new Intent(getApplicationContext(), Home.class);
                                startActivity(Skip);
                                finish();
                                break;

                            case R.id.Frame_CreateNewAccount:
                                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                                startActivity(intent);
                                finish();
                                break;

                            case R.id.Card_Login:

                                if (ValidateInputs()) {
                                    if (ValidateEmail()) {

                                        //TODO : LOGIN PROCESS STARTS HERE
                                        Login(Email.getText().toString(), Password.getText().toString());

                                    } else {
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please enter a valid Email Address", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                } else {
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please enter both Email and Password", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }

                        }

                    }
                });
    }

    boolean ValidateInputs() {

        if (Email.getText().length() > 0 && Password.getText().length() > 0)
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

    void Login(String _Email, String _Password) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Login.this, "Logging in");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login_REQUEST request = new Login_REQUEST(_Email, _Password);

        API api = retrofit.create(API.class);
        Call<Login_RESPONSE> call = api.Login(request);

        call.enqueue(new Callback<Login_RESPONSE>() {
            @Override
            public void onResponse(Call<Login_RESPONSE> call, Response<Login_RESPONSE> response) {

                dialog.dismiss();

                Login_RESPONSE response1 = response.body();

                try {
                    if (response1.getStatus()) {

                        //Save Token
                        ConfidentialDB.save_AccessToken(getApplicationContext(), response1.getToken());
                        ConfidentialDB.save_Email(getApplicationContext(), _Email);
                        ConfidentialDB.save_Password(getApplicationContext(), _Password);
                        ConfidentialDB.save_logStatus(getApplicationContext(), true);

                        //Open User Details Page
                        Intent OpenUser_Details = new Intent(getApplicationContext(), Home.class);
                        startActivity(OpenUser_Details);
                        finish();

                    } else {

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong password. Please try again.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong password. Please try again.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<Login_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect with Server. Please check you Internet Access.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}
