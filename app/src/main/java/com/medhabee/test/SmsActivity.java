package com.medhabee.test;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SmsActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSubmit,btnDatePicker, btnTimePicker;
    EditText editTextMsg, editTextPhone,editTextDate, editTextTime;
    int mYear, mMonth, mDay, mHour, mMinute,tYear, tMonth, tDay, tHour, tMinute;
    ContentValues contentValues ;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        editTextMsg = (EditText) findViewById(R.id.editTextMsg);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        btnDatePicker=(Button)findViewById(R.id.btnDate);
        btnDatePicker.setOnClickListener(this);
        editTextDate=(EditText)findViewById(R.id.editTextDate);
        btnTimePicker=(Button)findViewById(R.id.btnTime);
        btnTimePicker.setOnClickListener(this);
        editTextTime=(EditText)findViewById(R.id.editTextTime);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = openOrCreateDatabase("testApp",MODE_PRIVATE,null);
                db.execSQL("CREATE TABLE IF NOT EXISTS sms ( _id INTEGER PRIMARY KEY AUTOINCREMENT, phone VARCHAR NOT NULL, message VARCHAR NOT NULL,year INTEGER, month INTEGER, day INTEGER, hour INTEGER, minute INTEGER, status INTEGER NOT NULL,createdtime VARCHAR);");

                //db.execSQL("INSERT INTO sms (phone,message,year,month,day,hour,min,status) VALUES ('" +phn+"','" + msg  +"',"++");");

                String message = editTextMsg.getText().toString();
                String phone = editTextPhone.getText().toString();
                String ctime =  ""+System.currentTimeMillis();
                /*
                tYear= 2016;
                tMonth= 9-1;
                tDay= 25;
                tHour= 16;
                tMinute= 38;
                */

                contentValues = new ContentValues();
                contentValues.put("phone",phone);
                contentValues.put("message",message);
                contentValues.put("year",tYear);
                contentValues.put("month",tMonth+1);
                contentValues.put("day",tDay);
                contentValues.put("hour",tHour);
                contentValues.put("minute",tMinute);
                contentValues.put("status",0);
                contentValues.put("createdtime",ctime);
                db.insert("sms",null,contentValues);
                db.close();


                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.clear();
                calendar.set(Calendar.YEAR,tYear);
                calendar.set(Calendar.MONTH,tMonth);
                calendar.set(Calendar.DAY_OF_MONTH,tDay);
                calendar.set(Calendar.HOUR_OF_DAY,tHour);
                calendar.set(Calendar.MINUTE,tMinute);

                editTextPhone.setText("");
                editTextMsg.setText("");
                editTextDate.setText("");
                editTextTime.setText("");
                Toast.makeText(SmsActivity.this, "Your Message has been saved for sending", Toast.LENGTH_SHORT).show();
                //Toast.makeText(SmsActivity.this, tHour+":"+tMinute+" "+tDay+tMonth+tYear, Toast.LENGTH_LONG).show();

                /************************ Alarm Manager ***********************/

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(SmsActivity.this, AlarmReceiver.class);
                intent.putExtra("message",message);
                intent.putExtra("phone",phone);
                intent.putExtra("ctime",ctime);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SmsActivity.this,1805,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5000,pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        });
    }


    /*************Time and Date Picker*******************/
    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            tDay = dayOfMonth;
                            tMonth = monthOfYear;
                            tYear = year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnTimePicker) {

            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            editTextTime.setText(hourOfDay + ":" + minute);
                            tHour = hourOfDay;
                            tMinute = minute;
                        }
                    }, mHour, mMinute,false);
            timePickerDialog.show();
        }
    }

}
