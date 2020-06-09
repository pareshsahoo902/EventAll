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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.otta.eventall.Activities.ViewAll.ViewAllPage;
import com.otta.eventall.Model.SubCategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeSubCategory_Big_Adapter extends RecyclerView.Adapter {

    List<SubCategoryModel> SubCatList;
    static Context context;
    static Typeface font;
    static FragmentManager fragmentManager;




    public HomeSubCategory_Big_Adapter() {
    }

    public HomeSubCategory_Big_Adapter(Context context, FragmentManager fragmentManager, List<SubCategoryModel> SubCategoryList) {
        SubCatList = SubCategoryList;
        this.context = context;
        font = FontEngine.getSemiBoldFont(context);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory_big, parent, false);
        return new myViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;

        myViewHolder.selectedSubCategory = SubCatList.get(position);
        myViewHolder.SubCatName.setText(myViewHolder.selectedSubCategory.getSubCatName());

        if(position == 0)
            myViewHolder.LeftMargin.setVisibility(View.VISIBLE);
        else
            myViewHolder.LeftMargin.setVisibility(View.GONE);

        if(position == SubCatList.size() - 1)
            myViewHolder.RightMargin.setVisibility(View.VISIBLE);
        else
            myViewHolder.RightMargin.setVisibility(View.GONE);

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


    @Override
    public int getItemCount() {
        return SubCatList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        SubCategoryModel selectedSubCategory;
        TextView SubCatName;
        ImageView SubCatImage;
        FrameLayout LeftMargin , RightMargin;
        View Body;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Body= itemView;
            FontEngine.overrideTypeface(context, itemView);
            selectedSubCategory = new SubCategoryModel();
            SubCatImage = itemView.findViewById(R.id.Image_SubCatImage);
            SubCatName = itemView.findViewById(R.id.Text_SubCatName);

            LeftMargin = itemView.findViewById(R.id.Frame_LeftMargin);
            RightMargin = itemView.findViewById(R.id.Frame_RightMargin);

            LeftMargin.setVisibility(View.GONE);
            RightMargin.setVisibility(View.GONE);

        }
    }

}
