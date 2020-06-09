package com.otta.eventall.Activities.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otta.eventall.Activities.SubCategory.Adapter.SubCategoryCardView_Adapter;
import com.otta.eventall.Activities.SubCategory.Adapter.SubCategory_Adapter;
import com.otta.eventall.Model.SubCategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.FontEngine;

import java.util.ArrayList;

public class SubList_Cardview extends AppCompatActivity {

    RecyclerView SubCatRecyclerView;
    ImageView Back;
    SubCategoryCardView_Adapter adapter;
    TextView HeaderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list__cardview);

        FontEngine.overrideTypeface(getApplicationContext() ,getWindow().getDecorView());

        SubCatRecyclerView = findViewById(R.id.recyclerViewSubCat);
        Back = findViewById(R.id.BackImage);
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

        adapter = new SubCategoryCardView_Adapter(getApplicationContext() , list);
        SubCatRecyclerView.setAdapter(adapter);
    }
}
