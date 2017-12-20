package com.medhabee.test;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.AvoidXfermode;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by gaangchil on 9/20/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String message,phone,ctime;

    //The Android's default system path of your application database.
    //private static String DB_PATH =  //"/data/data/YOUR_PACKAGE/databases/";
    //private static String DB_NAME = "testApp";


    @Override
    public void onReceive(Context context, Intent intent) {
        message = intent.getExtras().getString("message").toString();
        phone = intent.getExtras().getString("phone").toString();
        ctime = intent.getExtras().getString("ctime").toString();
        Toast.makeText(context,"Message has been sent",Toast.LENGTH_LONG).show();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone,null,message,null,null);
        //SQLiteDatabase db =  SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //db.execSQL("UPDATE sms SET status=1 where createdtime='"+ctime+"';");
    }
}
