package com.otta.eventall.Activities.SubCategory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.otta.eventall.Activities.ViewAll.ViewAllPage;
import com.otta.eventall.Model.SubCategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class SubCategoryCardView_Adapter extends RecyclerView.Adapter {

    List<SubCategoryModel> SubCategoryList;
    static Context context;



    public SubCategoryCardView_Adapter() {
    }

    public SubCategoryCardView_Adapter(Context context, List<SubCategoryModel> subCategoryList) {

        SubCategoryList = subCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory_cardview, parent, false);
        return new myViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.selectedSubCategory = SubCategoryList.get(position);

        myViewHolder.SubCatName.setText(myViewHolder.selectedSubCategory.getSubCatName());
        Glide.with(context)
                .load(myViewHolder.selectedSubCategory.getSubCatImage())
                .into(myViewHolder.SubCatImage);


        PushDownAnim.setPushDownAnimTo(myViewHolder.Body)
                .setScale(MODE_SCALE,
                        PushDownAnim.DEFAULT_PUSH_SCALE)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent OpenBusinessDetails = new Intent(context , ViewAllPage.class);
                        OpenBusinessDetails.putExtra("CatName", myViewHolder.selectedSubCategory.getSubCatName());
                        OpenBusinessDetails.putExtra("CatImage" , myViewHolder.selectedSubCategory.getSubCatImage());
                        OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(OpenBusinessDetails);
                    }
                });
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return SubCategoryList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        SubCategoryModel selectedSubCategory;
        TextView SubCatName;
        ImageView SubCatImage;
        View Body;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Body = itemView;
            FontEngine.overrideTypeface(context, itemView);
            selectedSubCategory = new SubCategoryModel();
            SubCatName = itemView.findViewById(R.id.Text_SubCatName);
            SubCatImage = itemView.findViewById(R.id.Image_SubCatImage);

        }
    }
}
