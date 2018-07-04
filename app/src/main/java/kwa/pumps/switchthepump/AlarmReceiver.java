package kwa.pumps.switchthepump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lekshmi on 04-07-2018.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    private static final String TAG = "KWATest: AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        assert b != null;
        String No=b.getString("Number");

        Log.i(TAG, "onReceive: Making call");
        Toast.makeText(context, "ALARM", Toast.LENGTH_LONG).show();
        Toast.makeText(context, No, Toast.LENGTH_SHORT).show();
        // AppUtils.makeCall(context,No);
        AppUtils.dial(No,context);

    }
}
