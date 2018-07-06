package kwa.pumps.switchthepump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lekshmi on 06-07-2018.
 */

public class MessageReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {

        // Get the object of SmsManager
        final SmsManager sms = SmsManager.getDefault();

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    int duration = Toast.LENGTH_LONG;
                    if(senderNum.equalsIgnoreCase("+919074773148")) {
                        Toast toast = Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, duration);
                        toast.show();
                    }
                    // putSmsToDatabase(currentMessage,context);
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
    }

