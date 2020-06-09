package com.otta.eventall.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

public class PhotoViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        FontEngine.overrideTypeface(getApplicationContext() , getWindow().getDecorView());

        PhotoView photoView = findViewById(R.id.photo_view);
        ImageView CloseImage = findViewById(R.id.CloseImage);
        ProgressBar Prog = findViewById(R.id.Prog);

        Bundle bundle = getIntent().getExtras();

        String PhotoLink = bundle.getString("PhotoLink");

        Glide.with(getApplicationContext())
                .load(PhotoLink)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Prog.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(photoView);

        CloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
