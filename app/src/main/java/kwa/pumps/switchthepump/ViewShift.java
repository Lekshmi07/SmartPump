package kwa.pumps.switchthepump;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;

import kwa.pumps.switchthepump.database.DBManager;

public class ViewShift extends AppCompatActivity {

    private TableLayout tableLayout;
    TabHost newRow;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shift);

        tableLayout = (TableLayout) findViewById(R.id.viewtable);
        db = new DBManager(ViewShift.this);

        int numrows = db.numOfRows();
        Cursor cursor = db.viewData();

        if (numrows != 0) {
                try {
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {


                            String number_str = cursor.getString(cursor.getColumnIndex(db.MOBILE_NO));
                            String power_str = cursor.getString(cursor.getColumnIndex(db.POWER));
                            String pump_str = cursor.getString(cursor.getColumnIndex(db.PUMP));
                            String on_str = cursor.getString(cursor.getColumnIndex(db.TIME_ON));
                            String off_str = cursor.getString(cursor.getColumnIndex(db.TIME_OFF));

                            View tableRow = LayoutInflater.from(this).inflate(R.layout.activity_view_shift, null, false);
                            TextView Num = (TextView) tableRow.findViewById(R.id.Number);
                            TextView Power = (TextView) tableRow.findViewById(R.id.power);
                            TextView Pump = (TextView) tableRow.findViewById(R.id.pump);
                            TextView ON = (TextView) tableRow.findViewById(R.id.ON);
                            TextView OFF = (TextView) tableRow.findViewById(R.id.OFF);

                            Num.setText(number_str.toString());
                            Power.setText(power_str.toString());
                            Pump.setText(pump_str.toString());
                            ON.setText(on_str.toString());
                            OFF.setText(off_str.toString());
                            tableLayout.addView(tableRow);

                        }
                    }
                }catch (Exception e){

                }



        }
    }
}
