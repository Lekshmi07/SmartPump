package kwa.pumps.switchthepump;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lekshmi on 04-07-2018.
 */

public class AppUtils extends Activity
{
    private static final String TAG = "KWATest: AppUtils";

    DBManager db=new DBManager(AppUtils.this);

    void dial(String number, Context context) {



        try {
            number = new String(number.trim().replace(" ", "%20").replace("&", "%26")
                    .replace(",", "%2c").replace("(", "%28").replace(")", "%29")
                    .replace("!", "%21").replace("=", "%3D").replace("<", "%3C")
                    .replace(">", "%3E").replace("#", "%23").replace("$", "%24")
                    .replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
                    .replace(".", "%2E").replace("/", "%2F").replace(":", "%3A")
                    .replace(";", "%3B").replace("?", "%3F").replace("@", "%40")
                    .replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
                    .replace("_", "%5F").replace("`", "%60").replace("{", "%7B")
                    .replace("|", "%7C").replace("}", "%7D"));



    String no="tel:"+number;
    Uri uri = Uri.parse(no);
    Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
    Toast.makeText(context, number, Toast.LENGTH_SHORT).show();
    Intent i = new Intent(Intent.ACTION_CALL, uri);
    int ph= Integer.parseInt(number.substring(0,9));
    Cursor cur=db.getPowerStatus(ph);


    if(cur.getCount()!=0) {

        cur.moveToFirst();
        String power=cur.getString(cur.getColumnIndex(db.POWER));
        if(power=="ON") {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "makeCall: Calling Phone activity");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } else {
                Log.e(TAG, "makeCall: No permission");
                Toast.makeText(context, "No permission for Phone", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Context context1=getApplicationContext();
            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(context1, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context1, 0, intent, 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5*60 * 1000, pendingIntent);
        }
    }
        } catch (Exception e) {
            //getAlertDialog().setMessage("Invalid number");
            e.printStackTrace();
        }

    }
}
