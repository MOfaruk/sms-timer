package com.medhabee.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PendingSmsActionActivity extends AppCompatActivity {
    EditText editTextPhoneUp,editTextMessageUp,editTextDateUp,editTextTimeUp;
    Button btnUpdate,btnDelete,btnCancel;
    private String phn,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Long id = intent.getLongExtra("id",0)+1;
        setContentView(R.layout.activity_pending_sms_action);

        editTextPhoneUp = (EditText)findViewById(R.id.editTextPhoneUp);
        editTextMessageUp = (EditText)findViewById(R.id.editTextMessageUp);
        editTextDateUp = (EditText)findViewById(R.id.editTextDateUp);
        editTextTimeUp = (EditText)findViewById(R.id.editTextTimeUp);

        final SQLiteDatabase db = openOrCreateDatabase("testApp",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM sms where _id="+id+"",null);
        c.moveToFirst();

        editTextPhoneUp.setText(c.getString(c.getColumnIndex("phone")));
        editTextMessageUp.setText(c.getString(c.getColumnIndex("message")));
        editTextDateUp.setText(c.getString(c.getColumnIndex("year"))+"-"+c.getString(c.getColumnIndex("month"))+"-"+c.getString(c.getColumnIndex("day")));
        editTextTimeUp.setText(c.getString(c.getColumnIndex("hour"))+":"+c.getString(c.getColumnIndex("minute")));

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnCancel = (Button)findViewById(R.id.btnCancel);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phn = editTextPhoneUp.getText().toString();
                msg = editTextMessageUp.getText().toString();
                db.execSQL("UPDATE sms SET phone='"+phn+"',message='"+msg+"' where _id="+id+"");
                Intent nI = new Intent(PendingSmsActionActivity.this,ViewPendingActivity.class);
                startActivity(nI);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("Delete FROM sms where _id="+id+"");
                Intent nI2 = new Intent(PendingSmsActionActivity.this,ViewPendingActivity.class);
                startActivity(nI2);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nI3 = new Intent(PendingSmsActionActivity.this,ViewPendingActivity.class);
                startActivity(nI3);
            }
        });



    }
}
