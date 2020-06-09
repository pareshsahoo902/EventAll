package com.otta.eventall.Activities.ViewBusiness.EditPages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.otta.eventall.Activities.SignUp.User_ProfilePicture;
import com.otta.eventall.Activities.ViewBusiness.Adapter.Photos_Adapter;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.PhotosUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.PhotosUpdate_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.TitleUpdate_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Methods.UniversalUpdate_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_REQUEST;
import com.otta.eventall.Activities.ViewBusiness.Methods.SingleBusiness_RESPONSE;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.NewDesign;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.UserDetailsDB;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Photos extends AppCompatActivity {

    ImageView Back;
    CardView AddAPhoto;
    static FrameLayout EmptyPhotoFrame;

    private static final int TAKE_PICTURE1 = 112;
    static RecyclerView recyclerView;
    static Photos_Adapter adapter;

    static String _BusinessID;
    static String PhotosString;

    static ArrayList<String> PhotosArray;
    static Activity activity;

    static AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__photos);
        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());


        activity = Edit_Photos.this;

        Bundle bundle = getIntent().getExtras();
        _BusinessID = bundle.getString("BID");
        PhotosString = bundle.getString("PHOTOS");

        PhotosArray = new ArrayList<>();

        Init();
        Clickers();

        CleanPhotosString();
        ConvertStringToArray();

        SetAdapter();

    }

    void Init() {
        Back = findViewById(R.id.backAss);
        recyclerView = findViewById(R.id.photoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AddAPhoto = findViewById(R.id.Card_AddPhoto);
        EmptyPhotoFrame = findViewById(R.id.EmptyPhotoFrame);
    }

    void Clickers() {
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddAPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImageFromGallery();
            }
        });
    }

    void SelectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), TAKE_PICTURE1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();

                CropImage.activity(uri)
                        .setAspectRatio(4,3)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .start(this);
            }
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                //Upload To Firebase
                UploadProfilePictureToFirebase(result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    static void SetAdapter() {

        if(PhotosArray.size() > 0)
            EmptyPhotoFrame.setVisibility(View.GONE);
        else
            EmptyPhotoFrame.setVisibility(View.VISIBLE);

        adapter = new Photos_Adapter(activity,activity.getApplicationContext(), PhotosArray);
        recyclerView.setAdapter(adapter);
    }

    //Firebase
   static void UploadProfilePictureToFirebase(Uri ProfilePictureUri) {
        AlertDialog alertDialog = DialogHelper.getLoadingDialog(activity, "Uploading");
        alertDialog.show();

        StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference()
                .child("BusinessPhotos/" + UserDetailsDB.get_UserEmailAddress(activity.getApplicationContext()) + "/" + System.currentTimeMillis() + ".jpg");

        firebaseStorageRef.putFile(ProfilePictureUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        firebaseStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                alertDialog.dismiss();

                                String DownloadURl = task.getResult().toString();
                                AddNewLinkToPhotosArray(DownloadURl);
                                Update();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        alertDialog.dismiss();

                        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Unable to upload.Please try again", Snackbar.LENGTH_LONG);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.setDuration(100000);
                        snackbar.show();
                    }
                });

    }

    static void CleanPhotosString() {
        if (PhotosString != null) {
            PhotosString = PhotosString.replace("}}e", "");
        }
    }

    static void ConvertStringToArray() {

        PhotosArray = new ArrayList<>();

        if (PhotosString != null) {
            String[] SplitedPhotosString = PhotosString.split(",");
            if (SplitedPhotosString.length > 0) {
                for (int i = 0; i < SplitedPhotosString.length; i++) {
                        PhotosArray.add(SplitedPhotosString[i]);
                }
            }
        }
    }

    static void AddNewLinkToPhotosArray(String NewDownloadLink) {
        PhotosArray.add(NewDownloadLink);
    }

    public static void RemoveFromPhotosArray(int INDEX) {
        PhotosArray.remove(INDEX);
        Update();
    }

    static void Update() {
        //Convert Array To String

        String PhotosLinksInString = "";

        for (int i = 0; i < PhotosArray.size(); i++) {
            if (PhotosArray.size() > 0)
                PhotosLinksInString += PhotosArray.get(i) + ",";
            else
                PhotosLinksInString += PhotosArray.get(i);
        }

        final AlertDialog dialog = DialogHelper.getLoadingDialog(activity, "Updating");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotosUpdate_REQUEST request = new PhotosUpdate_REQUEST();
        request.setBusinessID(_BusinessID);
        request.setPhotosListInString(PhotosLinksInString);

        API api = retrofit.create(API.class);
        Call<PhotosUpdate_RESPONSE> call = api.Update_Photos("Bearer " + ConfidentialDB.get_AccessToken(activity.getApplicationContext()), request);

        call.enqueue(new Callback<PhotosUpdate_RESPONSE>() {
            @Override
            public void onResponse(Call<PhotosUpdate_RESPONSE> call, Response<PhotosUpdate_RESPONSE> response) {
                dialog.dismiss();

                PhotosUpdate_RESPONSE response1 = response.body();

                if (response1.isStatus()) {
                    FetchBusinessDetails(_BusinessID);
                } else {
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Unable to update. Please try again", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.setDuration(100000);
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<PhotosUpdate_RESPONSE> call, Throwable t) {
                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Unable to connect to server. Please check your internet connection", Snackbar.LENGTH_LONG);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Update();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });

    }


    static void FetchBusinessDetails(String BusinessID) {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(activity, "Refreshing");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BUSINESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SingleBusiness_REQUEST request = new SingleBusiness_REQUEST();
        request.setBusinessID(BusinessID);

        API api = retrofit.create(API.class);
        Call<SingleBusiness_RESPONSE> call = api.FetchSingleBusiness("Bearer " + ConfidentialDB.get_AccessToken(activity.getApplicationContext())
                , request);

        call.enqueue(new Callback<SingleBusiness_RESPONSE>() {
            @Override
            public void onResponse(Call<SingleBusiness_RESPONSE> call, Response<SingleBusiness_RESPONSE> response) {
                dialog.dismiss();

                SingleBusiness_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    PhotosString = response1.getSingleBusiness().getPhotos();

                    ConvertStringToArray();

                    if (PhotosArray.size() > 0) {
                        EmptyPhotoFrame.setVisibility(View.GONE);
                        SetAdapter();
                    } else
                        EmptyPhotoFrame.setVisibility(View.VISIBLE);


                } else {
                    Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Unable to load business details. Please try again", Snackbar.LENGTH_LONG);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FetchBusinessDetails(_BusinessID);
                        }
                    });
                    snackbar.setDuration(100000);
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<SingleBusiness_RESPONSE> call, Throwable t) {

                dialog.dismiss();

                Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Unable to connect to server. Please check your internet connection", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                    }
                });
                snackbar.setDuration(100000);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }



    public static void showConfirmDelete(Activity activity, int IndexToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_confirm_delete, null);

        CardView Confirm, Cancel;

        FontEngine.overrideTypeface(activity.getApplicationContext(), dialogLayout);
        builder.setView(dialogLayout);

        Confirm = dialogLayout.findViewById(R.id.Card_Confirm);
        Cancel = dialogLayout.findViewById(R.id.Card_Cancel);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                RemoveFromPhotosArray(IndexToRemove);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}
