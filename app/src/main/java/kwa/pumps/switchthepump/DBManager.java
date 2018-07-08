package kwa.pumps.switchthepump;

/**
 * Created by Lekshmi on 04-07-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.DatabaseUtils;
import android.database.Cursor;


public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "KWA.db";
    public static final String TABLE_SMS = "sms_table";
    public static final String MOBILE_NO = "phone";
    public static final String POWER = "power";
    public static final String PUMP = "pump";
    public static final String PENDING_INTENT_ON="alarmid1";
    public static final String PENDING_INTENT_OFF="alarmid2";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_SMS + "("
                + MOBILE_NO + " text primary key,"
                + POWER + " text,"
                + PUMP + " text,"
                + PENDING_INTENT_ON +" text,"
                + PENDING_INTENT_OFF +" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int numOfRows() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numOfRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_SMS);
        return numOfRows;
    }
    public boolean insertUserDetails(String no,String power, String pump, String indent_to_on, String intent_to_off) {

        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("insert into " +POCKET_DIARY_TABLE_REGISTRATION+ "(" +PLAYERDETAILS_COLUMN_NAME+ "," +PLAYERDETAILS_COLUMN_PIN+ ")values("+name+","+Pin+","+gender+","+highscore+")");
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOBILE_NO, no);
        contentValues.put(POWER, power);
        contentValues.put(PUMP, pump);
        contentValues.put(PENDING_INTENT_ON,indent_to_on);
        contentValues.put(PENDING_INTENT_ON,intent_to_off);

        db.insert(TABLE_SMS, null, contentValues);
        return  true;
    }

    public boolean getnumber(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        if (res.getCount()==0)
        {
            return false;
        }
        return true;
    }

    public Cursor getPowerStatus(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + POWER + " from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        return res;
    }

    public Cursor getPumpStatus(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + PUMP + " from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        return res;
    }

    public Cursor getPendingIntent(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + PENDING_INTENT_ON +"," +PENDING_INTENT_OFF + " from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        return res;
    }

    public void updatePumpStatus(String no, String pump) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUMP, pump);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void updatePowerStatus(String no, String power) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POWER, power);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addPendingIntent_ON(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_ON, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addPendingIntent_OFF(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_OFF, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }
  /*  public Cursor getDataUsername( String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + POCKET_DIARY_REGISTRATION_COLUMN_USERNAME + " from " + POCKET_DIARY_TABLE_REGISTRATION + " where " + POCKET_DIARY_REGISTRATION_COLUMN_USERNAME + " = " + "'" + name + "'", null);
        return res;
    }*/

}