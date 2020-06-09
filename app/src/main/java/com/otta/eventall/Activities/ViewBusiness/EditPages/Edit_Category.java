package com.otta.eventall.Activities.ViewBusiness.EditPages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.SubCategoryUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Category extends AppCompatActivity {

    CardView AddButton, SubCatCardView, SaveButton;
    ChipGroup entryChipGroup;
    Spinner CatSpinner, SubCatSpinner;
    String _BusinessID;
    ImageView back;
    ArrayList<String> CategoryList;
    boolean updateNeeded = false;

    String SelectedCategoryString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");

        CategoryList = new ArrayList<>();

        Init();
        Clickers();

        SelectedCategoryString = bundle.getString("CAT");

        if (SelectedCategoryString != null) {

            ConvertStringToList(SelectedCategoryString);

            SetChips(CategoryList);
        }


        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());
        SetSpinnerData_Catagory();
    }

    void ConvertStringToList(String CatString) {
        String[] Splited = CatString.split("@");

        for (int i = 0; i < Splited.length; i++) {
            CategoryList.add(Splited[i]);
        }

    }


    private String ConvertListToString(ArrayList<String> list) {

        String singleCatString = "";

        for (int k = 0; k < list.size(); k++) {
            if (singleCatString.length() == 0) {
                singleCatString += list.get(k);
            } else
                singleCatString += "@" + list.get(k);
        }

        return singleCatString;

    }


    void Init() {
        SaveButton = findViewById(R.id.SaveButton);
        SubCatCardView = findViewById(R.id.SubCatCardView);
        AddButton = findViewById(R.id.CatagoryAddButton);
        entryChipGroup = findViewById(R.id.chip_group);
        CatSpinner = findViewById(R.id.CatSpinner);
        SubCatSpinner = findViewById(R.id.SubCatSpinner);
        back = findViewById(R.id.back);
    }

    void Clickers() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SubCatCardView.getVisibility() == View.GONE)
                    if (CategoryList.contains(CatSpinner.getSelectedItem().toString())) {
                        Toast.makeText(getApplicationContext(), "This Category already added", Toast.LENGTH_SHORT).show();
                    } else {
                        CategoryList.add(CatSpinner.getSelectedItem().toString());
                        SetChips(CategoryList);
                        updateNeeded = true;
                    }
                else {
                    if (CategoryList.contains(SubCatSpinner.getSelectedItem().toString())) {
                        Toast.makeText(getApplicationContext(), "This Sub Category already added", Toast.LENGTH_SHORT).show();
                    } else {
                        CategoryList.add(SubCatSpinner.getSelectedItem().toString());
                        SetChips(CategoryList);
                        updateNeeded = true;
                    }
                }


            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (updateNeeded) {
                    if (CategoryList.size() > 0)
                        Update(ConvertListToString(CategoryList));
                    else {
                        Toast.makeText(getApplicationContext(), "Please select at least on Category", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    finish();
                }
            }
        });
    }


    void SetChips(ArrayList<String> CategoryList) {

        entryChipGroup.removeAllViews();

        for (int i = 0; i < CategoryList.size(); i++) {
            Chip newChip = getChip(entryChipGroup, CategoryList.get(i));
            entryChipGroup.addView(newChip);
            FontEngine.overrideTypeface(getApplicationContext(), entryChipGroup);
        }


    }

    void SetSpinnerData_Catagory() {

        ArrayList<String> CatList = new ArrayList<>();
        ArrayList<String> SubCatList = new ArrayList<>();

        for (int i = 0; i < ArrayPlace.AllCategoryList.size(); i++) {
            CatList.add(ArrayPlace.AllCategoryList.get(i).getCatName());
        }

        ArrayAdapter<String> CatspinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, CatList);
        CatSpinner.setAdapter(CatspinnerArrayAdapter);


        CatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SubCatList.clear();

                for (int k = 0; k < ArrayPlace.AllCategoryList.get(i).getSubCategoryList().size(); k++) {
                    SubCatList.add(ArrayPlace.AllCategoryList.get(i).getSubCategoryList().get(k).getSubCatName());
                }

                SetSpinnerData_SubCatagory(SubCatList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void SetSpinnerData_SubCatagory(ArrayList<String> SubCats) {

        if (SubCats.size() == 0) {
            SubCatCardView.setVisibility(View.GONE);
        } else {
            SubCatCardView.setVisibility(View.VISIBLE);

            ArrayAdapter<String> SubCatspinnerArrayAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, SubCats);
            SubCatSpinner.setAdapter(SubCatspinnerArrayAdapter);
        }
    }

    private Chip getChip(final ChipGroup entryChipGroup, String text) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.chip));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5,
                getResources().getDisplayMetrics()
        );
        chip.setBackgroundColor(Color.parseColor("#F3F3F3"));
        chip.setPadding(0, paddingDp, paddingDp, paddingDp);
        chip.setText(text);
        chip.setTextSize(12);
        chip.setTextColor(Color.WHITE);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNeeded = true;
                entryChipGroup.removeView(chip);
                CategoryList.remove(chip.getText().toString());
                Toast.makeText(getApplicationContext(), chip.getText().toString() + " removed", Toast.LENGTH_SHORT).show();
            }
        });
        return chip;
    }


    void Update(String CategoryString) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(Edit_Category.this, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubCategoryUpdate_REQUEST request = new SubCategoryUpdate_REQUEST();
        request.setBusinessId(_BusinessID);
        request.setSubCategory(CategoryString);

        API api = retrofit.create(API.class);
        Call<UniversalUpdate_RESPONSE> call = api.Update_SubCategory("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()), request);
        call.enqueue(new Callback<UniversalUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<UniversalUpdate_RESPONSE> call, Response<UniversalUpdate_RESPONSE> response) {
                dialog.dismiss();
                UniversalUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to update", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<UniversalUpdate_RESPONSE> call, Throwable t) {
                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect to server. Please check your internet connection", Snackbar.LENGTH_LONG);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Update(ConvertListToString(CategoryList));
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }
}
