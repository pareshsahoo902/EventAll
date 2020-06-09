package com.otta.eventall.Activities.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otta.eventall.Activities.SubCategory.Adapter.Category_Adapter;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.FontEngine;

import java.util.ArrayList;

public class AllCategoryList extends AppCompatActivity {


    RecyclerView SubCatRecyclerView;
    ImageView Back;
    Category_Adapter adapter;
    TextView SubCatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category_list);


        FontEngine.overrideTypeface(getApplicationContext() ,getWindow().getDecorView());

        SubCatRecyclerView = findViewById(R.id.recyclerViewSubCat);
        Back = findViewById(R.id.BackImage);
        SubCatCount = findViewById(R.id.Text_SubCatCount);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SubCatRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ArrayList<CategoryModel> list = new ArrayList<>();

        list.addAll(ArrayPlace.AllCategoryList);

        SubCatCount.setText(list.size() + " Categories");

        adapter = new Category_Adapter(getApplicationContext() , list);
        SubCatRecyclerView.setAdapter(adapter);

    }
}
