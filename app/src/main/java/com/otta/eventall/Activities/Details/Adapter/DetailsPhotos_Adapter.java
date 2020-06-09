package com.otta.eventall.Activities.Details.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.otta.eventall.Activities.PhotoViewer;
import com.otta.eventall.Activities.SubCategory.SubCategoryList;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

import java.util.List;

public class DetailsPhotos_Adapter extends RecyclerView.Adapter {

    List<String> PhotosList;
    Context context;

    RequestOptions op = new RequestOptions()
            .placeholder(R.drawable.image_placeholder_withoutlogo_small)
            .error(R.drawable.image_placeholder_withoutlogo_small);

    public DetailsPhotos_Adapter() {

    }

    public DetailsPhotos_Adapter(Context context, List<String> photosList) {
        PhotosList = photosList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_business_photos, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.PhotoURL = PhotosList.get(position);

        if (position == 0)
            myViewHolder.MarginLeft.setVisibility(View.VISIBLE);
        else myViewHolder.MarginLeft.setVisibility(View.GONE);

        if (position == PhotosList.size() - 1)
            myViewHolder.MarginRight.setVisibility(View.VISIBLE);
        else
            myViewHolder.MarginRight.setVisibility(View.GONE);

        Glide.with(context)
                .load(myViewHolder.PhotoURL)
                .apply(op)
                .into(myViewHolder.Photo);

        myViewHolder.Body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent preview = new Intent(context, PhotoViewer.class);
                preview.putExtra("PhotoLink", PhotosList.get(position));
                preview.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(preview);
            }
        });

    }

    @Override
    public int getItemCount() {
        return PhotosList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        String PhotoURL;
        CardView PhotoBody;
        ImageView Photo;
        FrameLayout MarginLeft, MarginRight;
        View Body;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Body = itemView;
            PhotoBody = itemView.findViewById(R.id.Frame_PhotoBody);
            Photo = itemView.findViewById(R.id.Image_Photo);
            MarginLeft = itemView.findViewById(R.id.Frame_LeftMargin);
            MarginRight = itemView.findViewById(R.id.Frame_RightMargin);

            MarginLeft.setVisibility(View.GONE);
            MarginRight.setVisibility(View.GONE);
        }
    }
}
