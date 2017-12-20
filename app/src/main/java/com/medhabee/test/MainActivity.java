package com.medhabee.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSMS, btnPendingSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSMS = (Button) findViewById(R.id.btnSMS);
        btnPendingSMS = (Button) findViewById(R.id.btnPendingSMS);

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,SmsActivity.class);
                startActivity(intent1);
            }
        });

        btnPendingSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,ViewPendingActivity.class);
                startActivity(intent2);
            }
        });

    }
}
