package com.otta.eventall.Activities.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otta.eventall.Activities.SubCategory.Adapter.SubCategory_Adapter;
import com.otta.eventall.Model.SubCategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.FontEngine;

import java.util.ArrayList;

import retrofit2.http.Header;

public class SubCategoryList extends AppCompatActivity {


    RecyclerView SubCatRecyclerView;
    ImageView Back;
    SubCategory_Adapter adapter;
    TextView SubCatCount, HeaderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_list);

        FontEngine.overrideTypeface(getApplicationContext() ,getWindow().getDecorView());

        SubCatRecyclerView = findViewById(R.id.recyclerViewSubCat);
        Back = findViewById(R.id.BackImage);
        SubCatCount = findViewById(R.id.Text_SubCatCount);
        HeaderText = findViewById(R.id.Text_HeaderText);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SubCatRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Bundle bundle = getIntent().getExtras();
        int SelectedCatgoryIndex = bundle.getInt("Index");
        HeaderText.setText(bundle.getString("CatName"));

        ArrayList<SubCategoryModel> list = new ArrayList<>();

        list.addAll(ArrayPlace.AllCategoryList.get(SelectedCatgoryIndex).getSubCategoryList());


        SubCatCount.setText(list.size() + " Sub Categories");


        adapter = new SubCategory_Adapter(getApplicationContext() , list);
        SubCatRecyclerView.setAdapter(adapter);

    }
}
