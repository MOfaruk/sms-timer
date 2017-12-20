package com.medhabee.test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gaangchil on 9/18/16.
 */
public class CustomAdapter extends ArrayAdapter<PendingSms>{
    private ArrayList<PendingSms> pendingSmses;
    private Activity activity;


    public CustomAdapter(Activity activity, ArrayList<PendingSms> pendingSmses) {
        super(activity, R.layout.singleitem, pendingSmses);
        this.pendingSmses = pendingSmses;
        this.activity= activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.singleitem,null,false);

            TextView textViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
            TextView textViewSms = (TextView) view.findViewById(R.id.textViewSms);
            TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);

            PendingSms p = pendingSmses.get(position);

            textViewPhone.setText(p.getPhone());
            textViewSms.setText(p.getSms());
            textViewTime.setText(p.getTimestamp());

        }
        return view;
    }
}
