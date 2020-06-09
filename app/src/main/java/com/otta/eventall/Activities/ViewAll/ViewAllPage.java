package com.otta.eventall.Activities.ViewAll;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.otta.eventall.Activities.Details.DetailsPage;
import com.otta.eventall.Activities.Search.Adapter.Search_Adapter;
import com.otta.eventall.Activities.Search.Methods.Search_REQUEST;
import com.otta.eventall.Activities.Search.Methods.Search_RESPONSE;
import com.otta.eventall.Activities.Search.Search;
import com.otta.eventall.Activities.ViewAll.Adapter.ViewAll_Adapter;
import com.otta.eventall.Activities.ViewAll.Methods.View_REQUEST;
import com.otta.eventall.Activities.ViewAll.Methods.View_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ViewAllPage extends AppCompatActivity {


    ImageView Back;
    TextView Category;
    FrameLayout EmptyFrame;
    KenBurnsView BackDropImage;
    RecyclerView recyclerView;
    ViewAll_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_page);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Clickers();
        SetData();
        FetchList();

    }

    void Init() {
        Back = findViewById(R.id.Image_Back);
        Category = findViewById(R.id.Category);
        BackDropImage = findViewById(R.id.image);
        EmptyFrame = findViewById(R.id.EmptyFrame);
        EmptyFrame.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.RV_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Category.setTypeface(FontEngine.getBoldFont(getApplicationContext()));

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void Clickers() {
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    void SetData() {
        Bundle bundle = getIntent().getExtras();
        Category.setText(bundle.getString("CatName"));

        Glide.with(getApplicationContext())
                .load(bundle.getString("CatImage"))
                .into(BackDropImage);

    }


    void FetchList() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(ViewAllPage.this, "Loading");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.CATEGORY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        View_REQUEST request = new View_REQUEST();
        request.setSubCategory(Category.getText().toString());

        API api = retrofit.create(API.class);
        Call<View_RESPONSE> call = api.FetchBusinessByCategory(request);
        call.enqueue(new Callback<View_RESPONSE>() {
            @Override
            public void onResponse(Call<View_RESPONSE> call, Response<View_RESPONSE> response) {

                dialog.dismiss();
                View_RESPONSE response1 = response.body();

                if(response1.isStatus()){
                    adapter= new ViewAll_Adapter(ViewAllPage.this, getApplicationContext() , response1.getResultList());
                    recyclerView.setAdapter(adapter);
                }else{
                    EmptyFrame.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<View_RESPONSE> call, Throwable t) {

                dialog.dismiss();

                Toast.makeText(getApplicationContext() , t.toString() , Toast.LENGTH_SHORT).show();
                EmptyFrame.setVisibility(View.VISIBLE);
            }
        });

    }
}
