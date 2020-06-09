package com.otta.eventall.Activities.AddBusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.otta.eventall.Activities.ViewBusiness.BusinessList;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

public class SuccessfullyBusinessAdded extends AppCompatActivity {

    FrameLayout Next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_business_added);


        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());


        Next = findViewById(R.id.Frame_Next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , BusinessList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
