package com.example.sabari.hostel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class hostel_search_activity extends AppCompatActivity {

    private EditText mvalue_field;
    private Button msearch_btn;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_search_activity);

        mvalue_field = findViewById(R.id.hs_value_field);
        msearch_btn = findViewById(R.id.hs_search_btn);

        final Bundle mbundle = new Bundle();
        msearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = mvalue_field.getText().toString();
                Intent resultintent = new Intent(hostel_search_activity.this, hostel_result_activity.class);
                mbundle.putString("sv",value);
                resultintent.putExtras(mbundle);
                startActivity(resultintent);
                /*if (TextUtils.isEmpty(value)) {
                    Intent resultintent = new Intent(hostel_search_activity.this, hostel_result_activity.class);
                    mbundle.putString("sv",value);
                    resultintent.putExtras(mbundle);
                    startActivity(resultintent);
                    mvalue_field.setError("this cant be empty sry!");
                }
                else{
                    //Toast.makeText(hostel_search_activity.this, "please enter text to search with", Toast.LENGTH_SHORT).show();
                    Intent resultintent = new Intent(hostel_search_activity.this, hostel_result_activity.class);
                    mbundle.putString("sv",value);
                    resultintent.putExtras(mbundle);
                    startActivity(resultintent);*/

                }

        });
    }
}
