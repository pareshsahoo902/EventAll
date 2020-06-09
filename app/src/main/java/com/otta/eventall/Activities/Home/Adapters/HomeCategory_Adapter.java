package com.otta.eventall.Activities.Home.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.otta.eventall.Activities.SubCategory.AllCategoryList;
import com.otta.eventall.Activities.SubCategory.SubList_Cardview;
import com.otta.eventall.Model.CategoryModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.ArrayPlace;
import com.otta.eventall.Utils.FontEngine;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;

public class HomeCategory_Adapter extends RecyclerView.Adapter {

    List<CategoryModel> CategoryList;
    static Context context;
    static Typeface font;
    static FragmentManager fragmentManager;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public HomeCategory_Adapter() {
    }

    public HomeCategory_Adapter(Context context, FragmentManager fragmentManager, List<CategoryModel> categoryList) {

        CategoryList = categoryList;
        //CategoryList.add(categoryList.get(categoryList.size()-1));
        this.context = context;
        font = FontEngine.getSemiBoldFont(context);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent, false);
            return new myViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_header, parent, false);
            return new myHeaderViewHolder(itemView);
        }

        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof myViewHolder) {

            myViewHolder myViewHolder = (myViewHolder) holder;
            myViewHolder.selectedCategory = CategoryList.get(position-1);
            myViewHolder.CatName.setText(myViewHolder.selectedCategory.getCatName().toUpperCase());
            //For Scrolling TextView you need this piece of code
            myViewHolder.CatName.setSelected(true);

            myViewHolder.ViewALlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SubList_Cardview.class);
                    intent.putExtra("Index", position-1);
                    intent.putExtra("CatName", myViewHolder.selectedCategory.getCatName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            if((position %2) != 0) {
                //SetSubCatList
                myViewHolder.subCategory_Small_adapter = new HomeSubCategory_Small_Adapter(context, fragmentManager, myViewHolder.selectedCategory.getSubCategoryList());
                myViewHolder.SubCatRecyclerView.setAdapter(myViewHolder.subCategory_Small_adapter);
            }else {
                //SetSubCatList
                myViewHolder.subCategory_Big_adapter = new HomeSubCategory_Big_Adapter(context, fragmentManager, myViewHolder.selectedCategory.getSubCategoryList());
                myViewHolder.SubCatRecyclerView.setAdapter(myViewHolder.subCategory_Big_adapter);
            }

            if(myViewHolder.selectedCategory.getSubCategoryList().size() == 0) {
                myViewHolder.body.setVisibility(View.GONE);
                myViewHolder.body.setLayoutParams(new RecyclerView.LayoutParams(0,0));
            }else{
                myViewHolder.body.setVisibility(View.VISIBLE);
                myViewHolder.body.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

        } else if (holder instanceof myHeaderViewHolder) {
            //cast holder to VHHeader and set data for header.

            myHeaderViewHolder headerViewHolder = (myHeaderViewHolder) holder;

            headerViewHolder.AllCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(context , AllCategoryList.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }
            });
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        CategoryModel selectedCategory;
        TextView CatName, ViewALlButton;
        FrameLayout MarginButton;
        RecyclerView SubCatRecyclerView;
        HomeSubCategory_Small_Adapter subCategory_Small_adapter;
        HomeSubCategory_Big_Adapter subCategory_Big_adapter;
        View body;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView;
            FontEngine.overrideTypeface(context, itemView);
            MarginButton = itemView.findViewById(R.id.bottommargin);
            selectedCategory = new CategoryModel();
            CatName = itemView.findViewById(R.id.Text_CatNameHeader);
            ViewALlButton = itemView.findViewById(R.id.ViewALLButton);
            SubCatRecyclerView = itemView.findViewById(R.id.Recyclerview_SubCat);
            SubCatRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            MarginButton.setVisibility(View.GONE);
        }
    }


    public static class myHeaderViewHolder extends RecyclerView.ViewHolder {
        ViewPager bannerViewPager;
        FrameLayout AllCategory;
        RecyclerView RecyclerView_HorizontalCategory;
        BannerAdapter bannerAdapter;
        DotsIndicator dotsIndicator;

        HomeHorizontalCategory_Adapter horizontalCategory_Adapter;

        public myHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            FontEngine.overrideTypeface(context, itemView);

            bannerViewPager = itemView.findViewById(R.id.bannerViewPager);
            AllCategory = itemView.findViewById(R.id.Frame_AllCategory);
            RecyclerView_HorizontalCategory = itemView.findViewById(R.id.RecyclerView_HorizontalCategory);
            RecyclerView_HorizontalCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            //Show Banners
            bannerAdapter = new BannerAdapter(fragmentManager);
            bannerViewPager.setAdapter(bannerAdapter);
            bannerViewPager.setCurrentItem(1);

            dotsIndicator = itemView.findViewById(R.id.dots_indicator);
            dotsIndicator.setViewPager(bannerViewPager);

            bannerViewPager.setClipToPadding(false);
            bannerViewPager.setPadding(40, 0, 40, 0);

            //Show Horizontal Category
            horizontalCategory_Adapter = new HomeHorizontalCategory_Adapter(context, ArrayPlace.AllCategoryList);
            RecyclerView_HorizontalCategory.setAdapter(horizontalCategory_Adapter);
        }
    }
}
