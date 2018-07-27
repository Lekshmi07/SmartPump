package kwa.pumps.switchthepump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.sql.SQLException;

import kwa.pumps.switchthepump.database.DBManager;
import kwa.pumps.switchthepump.models.Message;

/**
 * Created by Renjithnath on 7/16/2018.
 */

public class SMSListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        msgBody = msgBody.replace("EVENT HRS","EVENTHRS");
                        msgBody = msgBody.replace("\n","\",\"");
                        msgBody = msgBody.replace(" ","\"");
                        msgBody = "{\"" + msgBody + "\"}";
                        JSONObject msgJson = new JSONObject(msgBody);
                        Log.e("Incoming message", msgJson.toString());

                        Gson gson = new Gson();
                        Message message = gson.fromJson(msgBody, Message.class);
                        if(msg_from!=null && msg_from.length()>0){
                            message.setSender(msg_from);
                        }
                        Log.e("Incoming message", msgJson.toString());
                        Toast.makeText(context, "message received", Toast.LENGTH_SHORT).show();

                        DBManager dbManager = new DBManager(context);
                        try {
                            boolean isInserted = dbManager.insertUserDetails(message.getSender(),message.getPower(), message.getPump(),
                                    "", "", message.getErr(), message.getEventHrs(), message.getAuto(),
                                    message.getTm(), "", "", "");

                            if(isInserted){
                                Log.e("inserted", "successfully");
                                Toast.makeText(context, "message inserted to db", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
