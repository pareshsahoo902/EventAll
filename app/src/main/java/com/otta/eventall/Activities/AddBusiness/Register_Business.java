package com.otta.eventall.Activities.AddBusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.otta.eventall.R;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.FontEngine;

public class Register_Business extends AppCompatActivity {


    CardView SaveButton;
    EditText Name, BusinessName, Contact;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__business);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());

        Init();
        Clickers();
    }

    void Init() {
        Name = findViewById(R.id.Name);
        BusinessName = findViewById(R.id.BusinessTitle);
        Contact = findViewById(R.id.Number);
        Back = findViewById(R.id.BackImage);
        SaveButton = findViewById(R.id.SaveButton);
    }


    void Clickers() {
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ValidateAllFileds()) {

                    SaveDetailsinRequest();

                } else {
                    ShowEmptyAlert();
                }
            }
        });
    }


    boolean ValidateAllFileds() {
        if (Name.getText().length() > 0 &&
                BusinessName.getText().length() > 0 &&
                Contact.getText().length() > 9) {
            return true;
        } else return false;
    }


    void ShowEmptyAlert() {
        if (Name.getText().length() == 0)
            Toast.makeText(getApplicationContext(), "Please provide name", Toast.LENGTH_SHORT).show();
        else if (BusinessName.getText().length() == 0)
            Toast.makeText(getApplicationContext(), "Please provide business name", Toast.LENGTH_SHORT).show();
        else if (Contact.getText().length() < 10 )
            Toast.makeText(getApplicationContext(), "Please provide a valid contact number", Toast.LENGTH_SHORT).show();
    }


    void SaveDetailsinRequest() {

        ArrayPlace.Init_AddBusinessRequest();

        ArrayPlace.addNewBusiness_request.setContactPerson(Name.getText().toString());
        ArrayPlace.addNewBusiness_request.setBusinessTitle(BusinessName.getText().toString());
        ArrayPlace.addNewBusiness_request.setMobileNumber(Contact.getText().toString());

        Intent intent = new Intent(getApplicationContext(), Business_Address.class);
        startActivity(intent);
        finish();


    }
}

