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


    Button on,off;
    TimePicker timpick;
    EditText phone;
    private static final String TAG = "KWATest: AddShift";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 1);

        timpick=findViewById(R.id.PickTime);
        timpick.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        timpick.setCurrentMinute(now.get(Calendar.MINUTE));


        on=findViewById(R.id.ON);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                cal.set(
                        timpick.getCurrentHour(),
                        timpick.getCurrentMinute(),
                        0);

                phone=findViewById(R.id.Phone);
                String ph=phone.getText().toString()+",,,,1";
                int alarmID= (int) cal.getTimeInMillis();


                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Context context = getApplicationContext();
                Intent myIntent=new Intent(context,AlarmReceiver.class);
                myIntent.putExtra("Number",ph);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmID, myIntent, 0);
                assert manager!=null;
                manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmID,AlarmManager.INTERVAL_DAY, pendingIntent);

                Log.i(TAG, "onClick: Shift set for " + cal.getTimeInMillis());
                Toast.makeText(context, "Shift set", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
