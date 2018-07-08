package kwa.pumps.switchthepump;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    private String POWERON="ON";
    private String PUMPOFF="OFF";
    private int intent_off=0;
    private int intent_on=0;


    TimePicker setTime;
    Button bt_ON,bt_OFF;
    EditText Phone;
    private DBManager db;
    boolean mFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        final Context context = getApplicationContext();
        db=new DBManager(context);
        final int mValue = db.numOfRows();
        if (mValue == 0) {
            mFlag = true;
        } else {
            mFlag = false;
        }


        bt_ON=findViewById(R.id.ON);
        bt_OFF=findViewById(R.id.OFF);

        Calendar now=Calendar.getInstance();
        setTime=findViewById(R.id.PickTime);
        setTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        setTime.setCurrentMinute(now.get(Calendar.MINUTE));


        try {
            bt_ON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Phone = findViewById(R.id.Phone);
                    Calendar cal = Calendar.getInstance();

                    cal.set(Calendar.HOUR_OF_DAY, setTime.getCurrentHour());
                    cal.set(Calendar.MINUTE, setTime.getCurrentMinute());
                    Toast.makeText(context, "Alarm is set @" + cal.getTime(), Toast.LENGTH_SHORT).show();

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                    Intent myIntent = new Intent(context, AlarmReceiver.class);

                    String num=Phone.getText().toString();
                    String PhNo = num+",1";
                    myIntent.putExtra("Number", PhNo);

                    int alarmID = (int) cal.getTimeInMillis();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, myIntent, 0);


                    assert manager != null;
                    //manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                    manager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                    Toast.makeText(context, "Shift set", Toast.LENGTH_SHORT).show();


                    if(mFlag) {


                            db.insertUserDetails(num,POWERON , PUMPOFF, alarmID,intent_off);

                    }
                    else
                    {
                        if (db.getnumber(num) == true) {
                            db.addPendingIntent_ON(num, alarmID);
                        } else {
                            db.insertUserDetails(num, POWERON, PUMPOFF, alarmID,intent_off);
                        }

                    }
                }
            });

            bt_OFF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {


                    Phone = findViewById(R.id.Phone);
                    String num=Phone.getText().toString();
                    Calendar cal = Calendar.getInstance();

                    if (db.getnumber(num))
                    {
                    cal.set(Calendar.HOUR_OF_DAY, setTime.getCurrentHour());
                    cal.set(Calendar.MINUTE, setTime.getCurrentMinute());
                    Toast.makeText(context, "Alarm is set @" + cal.getTime(), Toast.LENGTH_SHORT).show();

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                    Intent myIntent = new Intent(context, AlarmReceiver.class);


                    String PhNo = num+",2";
                    myIntent.putExtra("Number", PhNo);

                    int alarmID = (int) cal.getTimeInMillis();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, myIntent, 0);


                    assert manager != null;
                    manager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


                    Toast.makeText(context, "Shift set", Toast.LENGTH_SHORT).show();


                    db.addPendingIntent_OFF(num,alarmID);
                    }
                    else {
                        Toast.makeText(context, "First set time to switch on the pump", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "Null value", Toast.LENGTH_SHORT).show();
        }


    }
}