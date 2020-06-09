package com.otta.eventall.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

public class MarketingUtil {


    public static void Call(Context context, String PhoneNumber) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91" + PhoneNumber, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    public static void Share(Activity activity, String MSGToSHare, String Location) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Check out " + MSGToSHare + " at " + Location + ".   I found this on EventAll App. You can download it from Google PlayStore";
        String shareSub = "EventAll";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }


    public static void OpenSMS(Activity activity, String PhoneNumber, String ContactPerson) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(activity); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello " + ContactPerson);

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "+91" + PhoneNumber);
            smsIntent.putExtra("sms_body", "Hello " + ContactPerson);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(smsIntent);
        }
    }
}
