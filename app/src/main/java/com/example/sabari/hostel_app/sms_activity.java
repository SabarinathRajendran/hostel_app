package com.example.sabari.hostel_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.Manifest;

public class sms_activity extends AppCompatActivity {

    private static final String TAG = "sms_activity" ;
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
    private String parentnumber;
    private String wardennumber;
    private DatabaseReference mdatabase;
    private TextView mparentnumber;
    private TextView mwardennumber;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

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
        mparentnumber = findViewById(R.id.sms_parentnumber);
        mwardennumber = findViewById(R.id.sms_wardennumber);

        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               checkForSmsPermission();
            }
        });

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Intent intent = getIntent();
        
        smsresult = intent.getStringExtra("smsresult");
        name = intent.getStringExtra("name");
        Rollno = intent.getStringExtra("rollno");

        mdatabase = FirebaseDatabase.getInstance().getReference().child("profile").child(Rollno);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parentnumber = dataSnapshot.child("f_no").getValue(String.class);
                wardennumber = dataSnapshot.child("war_no").getValue(String.class);

                mparentnumber.setText(parentnumber);
                mwardennumber.setText(wardennumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(sms_activity.this, "error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        
        switch (smsresult)
        {
            case "1": startleaveUI();
            break;
            case  "2": startremarkUI();
            break;
            default: starterrorUI();
        }
        
        }

    public void showDialog(Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

       builder.setTitle("Confirm");

        builder.setMessage("Do you really wanna send SMS for your number!");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendsms();
            }
        });
        builder.show();
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Error, permission not granted");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            showDialog(this);
        }
    }

    private void sendsms() {

        sendparentmessage();
        sendwardenmessage();

    }

    private void sendwardenmessage() {
        SmsManager sms = SmsManager.getDefault();
        String wardenmsg = mwardenmessage.getText().toString();
        sms.sendTextMessage(wardennumber, null, wardenmsg, null, null);
        Toast.makeText(this, "message sent successfully to "+ wardennumber, Toast.LENGTH_SHORT).show();
    }

    private void sendparentmessage() {
        SmsManager sms = SmsManager.getDefault();
        String parentmsg = mparentmessage.getText().toString();
        sms.sendTextMessage(parentnumber, null, parentmsg, null, null);
        Toast.makeText(this, "message sent successfully to "+ parentnumber, Toast.LENGTH_SHORT).show();
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
