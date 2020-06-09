package com.otta.eventall.Activities.ViewAll.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.bumptech.glide.request.RequestOptions;
import com.otta.eventall.Activities.Details.DetailsPage;
import com.otta.eventall.Activities.ViewAll.ViewAllPage;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.otta.eventall.Utils.MarketingUtil;
import com.thekhaeng.pushdownanim.PushDownAnim;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ViewAll_Adapter extends RecyclerView.Adapter {
    static Context context;
    Activity activity;
    List<BusinessModel> AllBusinessList;

    RequestOptions options = new RequestOptions()
            .error(R.drawable.image_placeholder_withoutlogo_small)
            .placeholder(R.drawable.image_placeholder_withoutlogo_small);

    public ViewAll_Adapter(Activity activity ,Context context, List<BusinessModel> allBusinessList) {
        this.context = context;
        this.activity = activity;
        AllBusinessList = allBusinessList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.selectedBusiness = AllBusinessList.get(position);

        myViewHolder.S_Title.setText(myViewHolder.selectedBusiness.getBusinessTitle());
        myViewHolder.S_Location.setText(myViewHolder.selectedBusiness.getArea() + ", " + myViewHolder.selectedBusiness.getCity());
        myViewHolder.S_Desc.setText(myViewHolder.selectedBusiness.getDesc());

        if(myViewHolder.S_Desc.getText().equals("") || myViewHolder.S_Desc.getText().length() == 0 )
            myViewHolder.S_Desc.setText("No description available");

        myViewHolder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MarketingUtil.Call(context , myViewHolder.selectedBusiness.getMobileNumber());
            }
        });
        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketingUtil.Share(activity, myViewHolder.selectedBusiness.getBusinessTitle() ,
                        myViewHolder.selectedBusiness.getArea() + ", " + myViewHolder.selectedBusiness.getCity());
            }
        });


        if(myViewHolder.selectedBusiness.getPhotos() != null){

            myViewHolder.selectedBusiness.getPhotos().replace("}}e","");

            String[] Split = myViewHolder.selectedBusiness.getPhotos().split(",");

            Glide.with(context)
                    .load(Split[0])
                    .apply(options)
                    .into(myViewHolder.S_Photo);
        }


        if(position == 0) {
            myViewHolder.TopMargin.setVisibility(View.VISIBLE);
            myViewHolder.S_TotalResult.setText("Total " + AllBusinessList.size() + " Results");
        }else{
            myViewHolder.TopMargin.setVisibility(View.GONE);
        }

        if(position == AllBusinessList.size()-1)
            myViewHolder.DownMargin.setVisibility(View.VISIBLE);
        else
            myViewHolder.DownMargin.setVisibility(View.GONE);


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

//                        Intent OpenBusinessDetails = new Intent(context , DetailsPage.class);
//                        OpenBusinessDetails.putExtra("BID", myViewHolder.selectedBusiness.getBusinessID());
//                        OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(OpenBusinessDetails);

                        Intent OpenBusinessDetails = new Intent(context , DetailsPage.class);
                        OpenBusinessDetails.putExtra("BMODEL", myViewHolder.selectedBusiness);
                        OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(OpenBusinessDetails);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return AllBusinessList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        BusinessModel selectedBusiness;

        TextView S_TotalResult;
        FrameLayout TopMargin , DownMargin;
        CardView share , Call;
        FrameLayout Body;
        ImageView S_Photo;
        TextView S_Title , S_Location , S_Desc ;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Body = itemView.findViewById(R.id.body);
            FontEngine.overrideTypeface(context, itemView);
            TopMargin = itemView.findViewById(R.id.TopMargin);
            DownMargin = itemView.findViewById(R.id.DownMargin);
            selectedBusiness = new BusinessModel();
            S_TotalResult = itemView.findViewById(R.id.S_Total);
            S_Photo = itemView.findViewById(R.id.S_Image);
            S_Title = itemView.findViewById(R.id.S_Title);
            S_Location = itemView.findViewById(R.id.S_Location);
            S_Desc = itemView.findViewById(R.id.S_Desc);
            Call = itemView.findViewById(R.id.S_Call);
            share = itemView.findViewById(R.id.S_Share);

            TopMargin.setVisibility(View.GONE);
            DownMargin.setVisibility(View.GONE);

        }
    }
}
