package com.example.sabari.hostel_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class spinnerAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> akeylist = new ArrayList<>();

    public spinnerAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        akeylist = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_hostel_fulldetail, null);
        TextView mkeyfield = (TextView) v.findViewById(R.id.lv_key_field);
        TextView mvaluefield = (TextView) v.findViewById(R.id.lv_value_field);
        mkeyfield.setText(akeylist.get(position).getAnimalName());
        mvaluefield.setText(akeylist.get(position).getAnimalImage());
        return v;

    }

}
