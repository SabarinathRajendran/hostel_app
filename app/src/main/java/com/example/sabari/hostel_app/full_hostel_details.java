package com.example.sabari.hostel_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class full_hostel_details extends AppCompatActivity {

    private DatabaseReference mdatabase;
    private ListView mlistview;
    private TextView mname;
    private TextView mrollno;
    private TextView mroomno;
    private ImageView mprofile_image;
    private StorageReference mStorageRef;
    public Uri downloaduri;
    private FloatingActionButton mleavebtn;
    private FloatingActionButton mremarkbtn;
    private Bundle mb;
    private String name;
    private String rollno;
    private String roomno;


    private ArrayList<Item> animalList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_hostel_details);

        Bundle b = getIntent().getExtras();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mname = findViewById(R.id.fh_name);
        mrollno = findViewById(R.id.fh_rollno);
        mroomno = findViewById(R.id.fh_roomno);
        mprofile_image = findViewById(R.id.fh_profileimage);
        mleavebtn = findViewById(R.id.fh_sendsms_leave);
        mremarkbtn = findViewById(R.id.fh_sendsms_remark);

        String toview = b.getString("rollno");

        final String[] smsresult = new String[1];

        mleavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsresult[0] = "1";
                startsmsactivity(smsresult[0]);
            }
        });

        mremarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsresult[0] = "2";
                startsmsactivity(smsresult[0]);
            }
        });

        mlistview = findViewById(R.id.fh_listview);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("profile").child(toview);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
                rollno = dataSnapshot.child("rno").getValue(String.class);
                roomno = "RoomNo : " + dataSnapshot.child("room_no").getValue(String.class);

                mname.setText(name);
                mrollno.setText(rollno);
                mroomno.setText(roomno);
                mStorageRef.child("profileimg/"+rollno+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloaduri = uri;
                        Picasso.get().load(downloaduri).into(mprofile_image);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(full_hostel_details.this, "error while getting name", Toast.LENGTH_SHORT).show();
            }
        });



        final CustomAdapter myadapter = new CustomAdapter(this,R.layout.hostel_result_view,animalList);
        mlistview.setAdapter(myadapter);

        mdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String mkeyvalue = dataSnapshot.getKey();
                String mvlue = dataSnapshot.getValue(String.class);

                animalList.add(new Item(mkeyvalue,mvlue));
                myadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startsmsactivity(String s) {
        Intent i = new Intent(full_hostel_details.this, sms_activity.class);
        i.putExtra("smsresult",s);
        i.putExtra("name",name);
        i.putExtra("rollno",rollno);
        startActivity(i);
    }
}
