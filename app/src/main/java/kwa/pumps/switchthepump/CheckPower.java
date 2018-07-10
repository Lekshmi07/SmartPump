package kwa.pumps.switchthepump;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;

/**
 * Created by Lekshmi on 10-07-2018.
 */

public class CheckPower extends Activity {
    DBManager db;
    private String POWER="ON";

    public boolean check(String number)
    {
        db=new DBManager(getApplicationContext());
        String power;
        Cursor cursor=db.getPowerStatus(number);

        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            power = cursor.getString(cursor.getColumnIndex(db.POWER));
            if (power.equalsIgnoreCase(POWER))
                return true;
            else
            {
                Context context1=getApplicationContext();
                AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(context1, AlarmReceiver.class);
               PendingIntent pendingIntent = PendingIntent.getBroadcast(context1, 0, intent, 0);
               alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5*60 * 1000, pendingIntent);
            }
        }
        return false;
    }
}
