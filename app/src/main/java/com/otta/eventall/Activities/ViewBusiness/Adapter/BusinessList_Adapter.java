package com.otta.eventall.Activities.ViewBusiness.Adapter;

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

import com.otta.eventall.Activities.Details.DetailsPage;
import com.otta.eventall.Activities.ViewBusiness.Business_Details;
import com.otta.eventall.Activities.ViewBusiness.Model.BusinessModel;
import com.otta.eventall.NewDesign;
import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class BusinessList_Adapter extends RecyclerView.Adapter {
    static Context context;
    List<BusinessModel> AllBusinessList;

    public BusinessList_Adapter(Context context, List<BusinessModel> allBusinessList) {
        this.context = context;
        AllBusinessList = allBusinessList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_businesslist, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.selectedBusiness = AllBusinessList.get(position);

        if (position == 0) {
            myViewHolder.TopLine.setVisibility(View.GONE);
            myViewHolder.MarginFrame.setVisibility(View.VISIBLE);
        }

        if (position == AllBusinessList.size() - 1) {
            myViewHolder.ButtomLine.setVisibility(View.GONE);
            myViewHolder.MarginFrameButtom.setVisibility(View.VISIBLE);
        }

        myViewHolder.BusinessName.setText(myViewHolder.selectedBusiness.getBusinessTitle());
        myViewHolder.Location.setText(myViewHolder.selectedBusiness.getArea() + ", " + myViewHolder.selectedBusiness.getCity());
        myViewHolder.Index.setText(position + 1 + "");

        if (myViewHolder.selectedBusiness.getSubCategory() == null)
            myViewHolder.ListedIn.setText("Not listed in any category");
        else{
            String[] spilte = myViewHolder.selectedBusiness.getSubCategory().split("@");
            myViewHolder.ListedIn.setText("Listed in " + spilte.length + " Categories");

        }



        PushDownAnim.setPushDownAnimTo(myViewHolder.body)
                .setScale(MODE_SCALE,
                        PushDownAnim.DEFAULT_PUSH_SCALE)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent OpenBusinessDetails = new Intent(context , NewDesign.class);
                        OpenBusinessDetails.putExtra("BID", myViewHolder.selectedBusiness.getBusinessID());
                        OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(OpenBusinessDetails);

//                        Intent OpenBusinessDetails = new Intent(context , DetailsPage.class);
//                        OpenBusinessDetails.putExtra("BMODEL", myViewHolder.selectedBusiness);
//                        OpenBusinessDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(OpenBusinessDetails);
                    }
                });


    }

    @Override
    public int getItemCount() {
        return AllBusinessList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        BusinessModel selectedBusiness;
        TextView BusinessName, ListedIn, Location, Index;
        CardView body;
        FrameLayout MarginFrame, MarginFrameButtom;
        ImageView TopLine, ButtomLine;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            body = itemView.findViewById(R.id.Body);
            FontEngine.overrideTypeface(context, itemView);
            selectedBusiness = new BusinessModel();
            MarginFrame = itemView.findViewById(R.id.MargingFrame);
            Index = itemView.findViewById(R.id.Text_IndexCount);
            BusinessName = itemView.findViewById(R.id.Text_BN);
            ListedIn = itemView.findViewById(R.id.Text_ListedIn);
            Location = itemView.findViewById(R.id.Text_LocationShort);
            TopLine = itemView.findViewById(R.id.TopLine);
            ButtomLine = itemView.findViewById(R.id.BottomLine);
            MarginFrameButtom = itemView.findViewById(R.id.MargingFrameBottom);
            MarginFrameButtom.setVisibility(View.GONE);
            MarginFrame.setVisibility(View.GONE);
        }
    }
}
