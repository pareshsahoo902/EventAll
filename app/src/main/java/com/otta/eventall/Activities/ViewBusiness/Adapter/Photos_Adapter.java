package com.otta.eventall.Activities.ViewBusiness.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otta.eventall.Activities.PhotoViewer;
import com.otta.eventall.Activities.ViewBusiness.EditPages.Edit_Photos;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.NewDesign;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Photos_Adapter extends RecyclerView.Adapter {

    static Context context;
    static Activity activity;
    List<String> PhotosList;

    public Photos_Adapter(Activity activity, Context context, List<String> Photos) {
        this.context = context;
        this.activity = activity;
        PhotosList = Photos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;

        Glide.with(context)
                .load(PhotosList.get(position))
                .into(myViewHolder.Image_Preview);

        PushDownAnim.setPushDownAnimTo(myViewHolder.Image_Preview, myViewHolder.Remove)
                .setScale(MODE_SCALE,
                        PushDownAnim.DEFAULT_PUSH_SCALE)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        switch (view.getId()){
                            case R.id.Card_Remove:
                                if (PhotosList.size() > 1)
                                    Edit_Photos.showConfirmDelete(activity,position);
                                else
                                    Toast.makeText(context , "At least one photo is needed. Add another one to remove this one.",Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.Image_preview:

                                Intent preview = new Intent(activity.getApplicationContext() , PhotoViewer.class);
                                preview.putExtra("PhotoLink" , PhotosList.get(position));
                                preview.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(preview);
                                break;

                        }

                         }
                });
    }

    @Override
    public int getItemCount() {
        return PhotosList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        CardView Remove;
        ImageView Image_Preview;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            FontEngine.overrideTypeface(context, itemView);
            Image_Preview = itemView.findViewById(R.id.Image_preview);
            Remove = itemView.findViewById(R.id.Card_Remove);
        }
    }
}
