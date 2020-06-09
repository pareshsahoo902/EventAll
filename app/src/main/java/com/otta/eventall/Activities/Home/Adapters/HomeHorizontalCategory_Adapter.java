package com.otta.eventall.Activities.Home.Adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.otta.eventall.Activities.SubCategory.AllCategoryList;
import com.otta.eventall.Activities.SubCategory.SubCategoryList;
import com.otta.eventall.Activities.ViewAll.ViewAllPage;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

import java.util.List;

public class HomeHorizontalCategory_Adapter extends RecyclerView.Adapter {

    List<CategoryModel> CategoryList;
    Context context;
    static Typeface font;

    public HomeHorizontalCategory_Adapter() {
    }

    public HomeHorizontalCategory_Adapter(Context context, List<CategoryModel> categoryList) {
        CategoryList = categoryList;
        this.context = context;
        font = FontEngine.getSemiBoldFont(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_horizontal, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.selectedCategory = CategoryList.get(position);

        myViewHolder.CatName.setText(myViewHolder.selectedCategory.getCatName());
        Glide.with(context)
                .load(myViewHolder.selectedCategory.getCatImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(myViewHolder.CatImage);

        //For Scrolling Textview you need this piece of code
        myViewHolder.CatName.setSelected(true);

        myViewHolder.Body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myViewHolder.selectedCategory.getSubCategoryList().size() > 0) {
                    Intent in = new Intent(context, SubCategoryList.class);
                    in.putExtra("Index", position);
                    in.putExtra("CatName", myViewHolder.selectedCategory.getCatName());
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }else{

                    Intent OpenBusinessDetails = new Intent(context , ViewAllPage.class);
                    OpenBusinessDetails.putExtra("CatName", myViewHolder.selectedCategory.getCatName());
                    OpenBusinessDetails.putExtra("CatImage" , myViewHolder.selectedCategory.getCatImage());
                    OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(OpenBusinessDetails);
                }


            }
        });




    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        CategoryModel selectedCategory;
        FrameLayout Body , AllCategory;
        TextView CatName;
        ImageView CatImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedCategory = new CategoryModel();
            Body = itemView.findViewById(R.id.Frame_CatBody);
            AllCategory = itemView.findViewById(R.id.Frame_AllCategory);
            CatName = itemView.findViewById(R.id.Text_CatName);
            CatImage = itemView.findViewById(R.id.Image_CatImage);
            CatName.setTypeface(font);
        }
    }
}
