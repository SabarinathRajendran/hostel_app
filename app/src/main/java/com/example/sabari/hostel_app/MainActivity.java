package com.example.sabari.hostel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mprofile;
    private Button mtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mprofile = findViewById(R.id.profile_view_btn);
        mtest = findViewById(R.id.mprofile_test);

        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileintent = new Intent(MainActivity.this, hostel_search_activity.class);
                startActivity(profileintent);
            }
        });

        mtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testleintent = new Intent(MainActivity.this, full_hostel_details.class);
                startActivity(testleintent);
            }
        });

    }
}
