package com.example.sabari.hostel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class sms_activity extends AppCompatActivity {
    
    private Bundle mbundle;
    private TextView mheading;
    private CheckBox mparentscheckbox;
    private CheckBox mwardencheckbox;
    private EditText mparentmessage;
    private EditText mwardenmessage;
    private Button msubmit;
    private String parentmessage;
    private String wardenmessage;
    private String studentname;
    private String name;
    private String Rollno;
    private String smsresult;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_activity);

        mheading = findViewById(R.id.sms_heading);
        mparentscheckbox = findViewById(R.id.sms_parentcb);
        mwardencheckbox = findViewById(R.id.sms_wardencb);
        mparentmessage = findViewById(R.id.sms_parentmessage);
        mwardenmessage = findViewById(R.id.sms_wardenmessage);
        msubmit = findViewById(R.id.sms_sendsmsbtn);
        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Intent intent = getIntent();
        
        smsresult = intent.getStringExtra("smsresult");
        name = intent.getStringExtra("name");
        Rollno = intent.getStringExtra("rollno");

        
        switch (smsresult)
        {
            case "1": startleaveUI();
            break;
            case  "2": startremarkUI();
            break;
            default: starterrorUI();
        }
        
        }

    private void starterrorUI() {
        mheading.setText("Error while setting this fields \n please contact the developer");
    }

    private void startremarkUI() {

        mheading.setText("Remark Message");

        parentmessage = "Your Son "+name+" Remarks \n 1.Not behaving well in the college";

        mparentmessage.setText(parentmessage);

        wardenmessage =name+" Remark \n 1.Not behaving well in the college";

        mwardenmessage.setText(wardenmessage);
    }

    private void startleaveUI() {
        mheading.setText("Leave Message");

        parentmessage = "Your Son "+name+" was absent today ("+date+") WITHOUT PRIOR INFORMATION. Please send your son with the Leave Letter. ";

        mparentmessage.setText(parentmessage);

        wardenmessage = name+" was absent today ("+date+") WITHOUT PRIOR INFORMATION. Please take care of this situation.";

        mwardenmessage.setText(wardenmessage);

    }
}
