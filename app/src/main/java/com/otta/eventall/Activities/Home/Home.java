package com.otta.eventall.Activities.Home;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.otta.eventall.Activities.Search.Search;
import com.otta.eventall.Activities.ViewBusiness.BusinessList;
import com.otta.eventall.Activities.Home.Adapters.HomeCategory_Adapter;
import com.otta.eventall.Activities.Login.Login;
import com.otta.eventall.Activities.Login.Methods.Logout_RESPONSE;
import com.otta.eventall.Activities.SignUp.SignUp;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.UserDetailsDB;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Home extends AppCompatActivity {

    //Navigation Drawer
    DrawerLayout dl;
    NavigationView nv;
    FrameLayout NV_MyBusinesses, NV_Logout, NV_Login, NV_CreateNewAccount;
    FrameLayout NV_Panel_LoggedIn, NV_Panel_NotLoggedIn;
    TextView Text_LoggedInUsername , Text_LoggedInUserEmail;
    ImageView Image_LoggedInUserProfilePicture;
    FrameLayout SearchFrame;

    //RecyclerView
    RecyclerView recyclerView;
    HomeCategory_Adapter adapter;

    //Others
    ImageView NotificationButton, MenuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        NavigationDrawerStuff();
        Clickers();

        ShowHomeCategories(ArrayPlace.AllCategoryList);

    }


    void Init() {

        //Navigation Drawer
        dl = findViewById(R.id.dl);
        nv = findViewById(R.id.nv);

        NV_MyBusinesses = nv.findViewById(R.id.NV_MyBusinesses);
        NV_Logout = nv.findViewById(R.id.NV_Logout);
        NV_Login = nv.findViewById(R.id.NV_Login);
        NV_CreateNewAccount = nv.findViewById(R.id.NV_CreateANewAccount);
        NV_Panel_LoggedIn = nv.findViewById(R.id.Frame_LoggedIn);
        NV_Panel_NotLoggedIn = nv.findViewById(R.id.Frame_NotLoggedin);
        Text_LoggedInUsername = nv.findViewById(R.id.Text_LoggedInUsername);
        Text_LoggedInUserEmail = nv.findViewById(R.id.Text_LoggedInUserEmail);
        Image_LoggedInUserProfilePicture = nv.findViewById(R.id.Image_LoggedInUserProfilePicture);

        SearchFrame = findViewById(R.id.Frame_Search);
        SearchFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Search.class);
                startActivity(intent);
            }
        });

        //Header
        NotificationButton = findViewById(R.id.NotificationButton);
        MenuButton = findViewById(R.id.MenuButton);
        //img = findViewById(R.id.img);

        //Home RecyclerView
        recyclerView = findViewById(R.id.Recyclerview_VerticalCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    void NavigationDrawerStuff() {
        View v = nv.getHeaderView(0);
        FontEngine.overrideTypeface(getApplicationContext(), v);

        if (ConfidentialDB.get_logStatus(getApplicationContext())) {
            //Logged In
            NV_Panel_LoggedIn.setVisibility(View.VISIBLE);
            NV_Panel_NotLoggedIn.setVisibility(View.GONE);
        } else {
            //Not Logged In
            NV_Panel_LoggedIn.setVisibility(View.GONE);
            NV_Panel_NotLoggedIn.setVisibility(View.VISIBLE);
        }

        Text_LoggedInUsername.setText(UserDetailsDB.get_Username(getApplicationContext()));
        Text_LoggedInUserEmail.setText(UserDetailsDB.get_UserEmailAddress(getApplicationContext()));


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder)
                .transform(new CircleCrop());

        Glide.with(getApplicationContext())
                .load(UserDetailsDB.get_ProfilePictureURL(getApplicationContext()))
                .apply(requestOptions)
                .into(Image_LoggedInUserProfilePicture);
    }

    void Clickers() {

        PushDownAnim.setPushDownAnimTo(MenuButton, NotificationButton, NV_MyBusinesses,
                NV_Logout, NV_Login, NV_CreateNewAccount)
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

                            case R.id.MenuButton:

                                dl.openDrawer(Gravity.LEFT);

                                break;

                            case R.id.NotificationButton:

                                Intent intent = new Intent(getApplicationContext(), Notification.class);
                                startActivity(intent);
                                break;

                            case R.id.NV_MyBusinesses:
                                dl.closeDrawer(Gravity.LEFT);
                                Intent AddBusiness = new Intent(getApplicationContext(), BusinessList.class);
                                startActivity(AddBusiness);
                                break;

                            case R.id.NV_Logout:
                                dl.closeDrawer(Gravity.LEFT);
                                LogoutMyself();
                                break;

                            case R.id.NV_Login:
                                dl.closeDrawer(Gravity.LEFT);
                                Intent login = new Intent(getApplicationContext(), Login.class);
                                startActivity(login);
                                finish();
                                break;

                            case R.id.NV_CreateANewAccount:

                                dl.closeDrawer(Gravity.LEFT);
                                Intent signUp = new Intent(getApplicationContext(), SignUp.class);
                                startActivity(signUp);
                                finish();

                                break;
                        }
                    }
                });
    }

    void ShowHomeCategories(List<CategoryModel> list) {

        ArrayList<CategoryModel> tempList = new ArrayList<>();
        tempList.addAll(list);

        tempList.add(list.get(list.size()-1));

        adapter = new HomeCategory_Adapter(getApplicationContext(), getSupportFragmentManager(), tempList);
        recyclerView.setAdapter(adapter);
    }


    void LogoutMyself() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Home.this, "Logging Out");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<Logout_RESPONSE> call = api.logout("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()));
        call.enqueue(new Callback<Logout_RESPONSE>() {
            @Override
            public void onResponse(Call<Logout_RESPONSE> call, Response<Logout_RESPONSE> response) {

                dialog.dismiss();

                ConfidentialDB.save_logStatus(getApplicationContext(), false);
                ConfidentialDB.save_AccessToken(getApplicationContext(), null);

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Logout_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                ConfidentialDB.save_logStatus(getApplicationContext(), false);
                ConfidentialDB.save_AccessToken(getApplicationContext(), null);

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
