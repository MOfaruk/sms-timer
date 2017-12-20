package com.medhabee.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewPendingActivity extends AppCompatActivity {
    private ListView listViewPendingSms;
    private ArrayList<PendingSms> dataSource;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending);


        listViewPendingSms = (ListView) findViewById(R.id.listViewPendingSms);
        dataSource = getAllSms();
        adapter = new CustomAdapter(this,dataSource);
        //if(!dataSource.isEmpty())
            listViewPendingSms.setAdapter(adapter);
        listViewPendingSms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewPendingActivity.this,PendingSmsActionActivity.class);
                //Toast.makeText(ViewPendingActivity.this,id+"",Toast.LENGTH_SHORT).show();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


    }

    private ArrayList<PendingSms> getAllSms(){
        ArrayList<PendingSms> allSms = new ArrayList<PendingSms>();

        SQLiteDatabase db = openOrCreateDatabase("testApp",MODE_PRIVATE,null);
            try {
                Cursor c = db.rawQuery("SELECT * FROM sms", null);
                c.moveToFirst();
                do {
                    //Log.d("JEEM",c.getString(c.getColumnIndex("message")));
                    //Log.d("JEEM",c.getString(c.getColumnIndex("phone")));
                    String msg = c.getString(c.getColumnIndex("message"));
                    String phone = c.getString(c.getColumnIndex("phone"));
                    String tms = c.getString(c.getColumnIndex("year")) + "/" + c.getString(c.getColumnIndex("month")) + "/" + c.getString(c.getColumnIndex("day"))
                            + " " + c.getString(c.getColumnIndex("hour")) + ":" + c.getString(c.getColumnIndex("minute"));
                    PendingSms ps = new PendingSms(phone, msg, tms);
                    allSms.add(ps);

                } while (c.moveToNext());
                db.close();
            }
            catch (Exception e)
            {
                Intent nIntent = new Intent(ViewPendingActivity.this,MainActivity.class);
                Toast.makeText(ViewPendingActivity.this,"No Message!",Toast.LENGTH_SHORT).show();
                startActivity(nIntent);
            }
        /*
        PendingSms ps1 = new PendingSms("5000","Hello 5000","10:50");
        PendingSms ps2 = new PendingSms("9999","Hi 9999","11:50");
        PendingSms ps3 = new PendingSms("121","Hello 121","10:10");
        PendingSms ps4 = new PendingSms("8080","Hi 8080","11:50");
        sms.add(ps1);
        sms.add(ps2);
        sms.add(ps3);
        sms.add(ps4);
        */
        return allSms;
    }
}
