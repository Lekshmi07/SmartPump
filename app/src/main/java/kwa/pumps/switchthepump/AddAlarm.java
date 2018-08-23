package kwa.pumps.switchthepump;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import kwa.pumps.switchthepump.database.DBManager;

public class AddAlarm extends AppCompatActivity {

    private String POWERON="ON";
    private String PUMPOFF="OFF";
    private String intent_off="000";
    private String time_off="000";



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

        final Calendar now=Calendar.getInstance();
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
                    cal.set(Calendar.SECOND,00);


                    if (cal.compareTo(now) <= 0) {
                        //Today Set time passed, count to tomorrow
                        cal.add(Calendar.DATE, 1);
                    }
                    String time=cal.getTime().toString();
                    time=time.substring(11,19) ;
                    Toast.makeText(context, "Alarm is set @" + time, Toast.LENGTH_SHORT).show();

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                    Intent myIntent = new Intent(context, AlarmReceiver.class);

                    String num = Phone.getText().toString();
                    String PhNo = num+",1";
                    myIntent.putExtra("Number", PhNo);

                    int alarmID = (int) cal.getTimeInMillis();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                    assert manager != null;
                    //manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                    manager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                    Toast.makeText(context, "Shift set", Toast.LENGTH_SHORT).show();
                    String alarmID_to_on= String.valueOf(alarmID);


                    if(mFlag) {


                        db.insertUserDetails(num,POWERON , PUMPOFF, alarmID_to_on,"", "", "", "", "", intent_off,time,time_off);
                    }
                    else
                    {
                        if (db.getnumber(num) == true) {
                            db.addPendingIntent_ON(num, alarmID_to_on);
                            db.addTime_ON(num,time);
                        } else {
                            db.insertUserDetails(num, POWERON, PUMPOFF, alarmID_to_on,"", "", "", "", "", intent_off,time,time_off);
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
                        cal.set(Calendar.SECOND,00);

                        if (cal.compareTo(now) <= 0) {
                            //Today Set time passed, count to tomorrow
                            cal.add(Calendar.DATE, 1);
                        }

                        String time=cal.getTime().toString();
                        time=time.substring(11,19) ;
                        Toast.makeText(context, "Alarm is set @" + time, Toast.LENGTH_SHORT).show();


                        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                        Intent myIntent = new Intent(context, AlarmReceiver.class);


                        String PhNo = num+",2";
                        myIntent.putExtra("Number", PhNo);

                        int alarmID = (int) cal.getTimeInMillis();
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                        assert manager != null;
                        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


                        Toast.makeText(context, "Shift set", Toast.LENGTH_SHORT).show();

                        String alarmID_to_off= String.valueOf(alarmID);


                        db.addPendingIntent_OFF(num,alarmID_to_off);
                        db.addTime_OFF(num,time);
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