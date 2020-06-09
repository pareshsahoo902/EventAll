package com.otta.eventall.Activities.SignUp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.otta.eventall.Activities.Home.Home;
import com.otta.eventall.Activities.SignUp.Methods.UserProfilePicture_REQUEST;
import com.otta.eventall.Activities.SignUp.Methods.UserProfilePicture_RESPONSE;
import com.otta.eventall.Helpers.DialogHelper;
import com.otta.eventall.R;
import com.otta.eventall.RetroFit.API;
import com.otta.eventall.SlashScreen;
import com.otta.eventall.Utils.ConfidentialDB;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.UserDetailsDB;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class User_ProfilePicture extends AppCompatActivity {


    CardView BrowseImage, ProPicCard;
    ImageView ProPic;
    CardView Save;
    private static final int TAKE_PICTURE1 = 111;
    Uri SelectedPhotoFilePath = null;

    String DownloadURl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_profile_pic);

        FontEngine.overrideTypeface(getApplicationContext(), getWindow().getDecorView());


        FirebaseApp.initializeApp(getApplicationContext());

        Init();
        Clickers();
    }


    void Init() {
        BrowseImage = findViewById(R.id.BrowseImageButton);
        ProPicCard = findViewById(R.id.ProPicCard);
        ProPic = findViewById(R.id.ProPic);
        Save = findViewById(R.id.SaveButton);
    }

    void Clickers() {
        PushDownAnim.setPushDownAnimTo(BrowseImage, ProPicCard, Save)
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

                            case R.id.BrowseImageButton:
                                SelectImageFromGalery();
                                break;

                            case R.id.ProPicCard:
                                SelectImageFromGalery();
                                break;

                            case R.id.SaveButton:

                                if(SelectedPhotoFilePath != null) {

                                    UploadProfiePictureToFirebase(SelectedPhotoFilePath);
                                }else
                                {
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select a profile picture.", Snackbar.LENGTH_LONG);
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

                                break;

                        }
                    }
                });

    }


    void SelectImageFromGalery() {

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
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(this);
            }
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                SelectedPhotoFilePath = result.getUri();

                Glide.with(getApplicationContext())
                        .load(SelectedPhotoFilePath)
                        .into(ProPic);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    //Firebase
    void UploadProfiePictureToFirebase(Uri ProfilePictureUri){
        AlertDialog alertDialog  = DialogHelper.getLoadingDialog(User_ProfilePicture.this, "Uploading");
        alertDialog.show();

        StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference()
                .child("Profile_Pics/" + UserDetailsDB.get_UserEmailAddress(getApplicationContext()) + "/" + System.currentTimeMillis() + ".jpg" );

        firebaseStorageRef.putFile(ProfilePictureUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        firebaseStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                alertDialog.dismiss();
                                DownloadURl = task.getResult().toString();
                                UserDetailsDB.save_ProfilePictureURL(getApplicationContext() , DownloadURl);

                                SaveUserProfilePicture();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        alertDialog.dismiss();

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to upload.Please try again", Snackbar.LENGTH_LONG);
                        snackbar.setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UploadProfiePictureToFirebase(SelectedPhotoFilePath);
                            }
                        });
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.setDuration(100000);
                        snackbar.show();
                    }
                });

    }


    //Server Stuff ----------------------------------------------

    void SaveUserProfilePicture() {

        final AlertDialog dialog = DialogHelper.getLoadingDialog(User_ProfilePicture.this, "Uploading");
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserProfilePicture_REQUEST request = new UserProfilePicture_REQUEST();
        request.setPhoto(DownloadURl);

        API api = retrofit.create(API.class);
        Call<UserProfilePicture_RESPONSE> call = api.Save_UserProfilePicture("Bearer " + ConfidentialDB.get_AccessToken(getApplicationContext()) , request);

        call.enqueue(new Callback<UserProfilePicture_RESPONSE>() {
            @Override
            public void onResponse(Call<UserProfilePicture_RESPONSE> call, Response<UserProfilePicture_RESPONSE> response) {
                dialog.dismiss();
                UserProfilePicture_RESPONSE response1 = response.body();

                if (response1.isStatus()) {

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();

                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to upload your profile picture. Please try again.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SaveUserProfilePicture();
                        }
                    });
                    snackbar.setActionTextColor(Color.WHITE);
                    snackbar.setDuration(100000);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<UserProfilePicture_RESPONSE> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Unable to connect with Server. Please check you Internet Access.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}
