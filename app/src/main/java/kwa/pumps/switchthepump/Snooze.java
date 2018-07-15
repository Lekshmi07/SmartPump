package kwa.pumps.switchthepump;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Lekshmi on 15-07-2018.
 */

public class Snooze extends Activity
{
    void snooze(String no, Context context) {

        try {


            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmManager.class);
            intent.putExtra("Number", no);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5 * 60 * 1000, pendingIntent);
            Toast.makeText(context, "Snooze...", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "NullPointer", Toast.LENGTH_SHORT).show();
        }
    }
}
