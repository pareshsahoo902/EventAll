package com.otta.eventall.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.otta.eventall.R;
import com.otta.eventall.Utils.FontEngine;

public class DialogHelper {

    static AlertDialog alertDialog;

    public static AlertDialog getCodeSendingDialog(Activity activity){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_loading, null);

        TextView MSG =  dialogLayout.findViewById(R.id.TextField);
        MSG.setTypeface(FontEngine.getSemiBoldFont(activity.getApplicationContext()));
        MSG.setText("Sending Code");
        builder.setView(dialogLayout);

        alertDialog =  builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        return alertDialog;
    }

    public static AlertDialog getLoadingDialog(Activity activity, String MSGText){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_loading, null);

        TextView  MSG =  dialogLayout.findViewById(R.id.TextField);
        MSG.setTypeface(FontEngine.getSemiBoldFont(activity.getApplicationContext()));
        MSG.setText(MSGText);
        builder.setView(dialogLayout);

        alertDialog =  builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        return alertDialog;
    }

    public static void ShowDevInfo(Context context){
        Toast.makeText(context,"Developed By Anurag Otta.( Kandarpur, Cuttack) Email : anuragotta@gmail.com",Toast.LENGTH_SHORT).show();
    }

}
