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
import com.otta.eventall.Activities.SubCategory.SubCategoryList;
import com.otta.eventall.Activities.ViewAll.ViewAllPage;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.Model.SubCategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter {

    List<CategoryModel> SubCategoryList;
    static Context context;

    public Category_Adapter() {
    }

    public Category_Adapter(Context context, List<CategoryModel> subCategoryList) {

        SubCategoryList = subCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.selectedSubCategory = SubCategoryList.get(position);

        myViewHolder.SubCatName.setText(myViewHolder.selectedSubCategory.getCatName());
        Glide.with(context)
                .load(myViewHolder.selectedSubCategory.getCatImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(myViewHolder.SubCatImage);


        myViewHolder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myViewHolder.selectedSubCategory.getSubCategoryList().size() > 0) {
                    Intent in = new Intent(context, com.otta.eventall.Activities.SubCategory.SubCategoryList.class);
                    in.putExtra("Index", position);
                    in.putExtra("CatName", myViewHolder.selectedSubCategory.getCatName());
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }else{
                    Intent OpenBusinessDetails = new Intent(context , ViewAllPage.class);
                    OpenBusinessDetails.putExtra("CatName", myViewHolder.selectedSubCategory.getCatName());
                    OpenBusinessDetails.putExtra("CatImage" , myViewHolder.selectedSubCategory.getCatImage());
                    OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(OpenBusinessDetails);
                }
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
        CategoryModel selectedSubCategory;
        TextView SubCatName;
        ImageView SubCatImage;
        View body;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            FontEngine.overrideTypeface(context, itemView);

            body = itemView;
            selectedSubCategory = new CategoryModel();
            SubCatName = itemView.findViewById(R.id.Text_SubCatName);
            SubCatImage = itemView.findViewById(R.id.Image_SubCatImage);

        }
    }
}
