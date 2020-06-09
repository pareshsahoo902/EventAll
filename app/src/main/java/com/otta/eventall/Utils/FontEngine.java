package com.otta.eventall.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class FontEngine {


    public static Typeface getMediumFont(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Medium.ttf");
    }

    public static Typeface getRegularFont(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Regular.ttf");
    }

    public static Typeface getSemiBoldFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-SemiBold.ttf");
    }

    public static Typeface getBoldFont(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Bold.ttf");
    }

    public static Typeface getBlackFont(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Black.ttf");
    }


    public static void overrideTypeface(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideTypeface(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(getSemiBoldFont(context));
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(getSemiBoldFont(context));
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(getSemiBoldFont(context));
            }else if (v instanceof Chip) {
                ((Chip) v).setTypeface(getSemiBoldFont(context));
            }
        } catch (Exception e) { }
    }


}
