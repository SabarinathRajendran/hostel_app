package com.example.sabari.hostel_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class full_hostel_details extends AppCompatActivity {

    private DatabaseReference mdatabase;
    private ListView mlistview;
    private TextView mname;
    private TextView mrollno;
    private TextView mroomno;
    private ImageView mprofile_image;

    private ArrayList<Item> animalList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_hostel_details);

        Bundle b = getIntent().getExtras();

        mname = findViewById(R.id.fh_name);
        mrollno = findViewById(R.id.fh_rollno);
        mroomno = findViewById(R.id.fh_roomno);

        String toview = b.getString("rollno");

        mlistview = findViewById(R.id.fh_listview);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("profile").child(toview);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String rollno = dataSnapshot.child("rno").getValue(String.class);
                String roomno = "RoomNo : " + dataSnapshot.child("room_no").getValue(String.class);

                mname.setText(name);
                mrollno.setText(rollno);
                mroomno.setText(roomno);
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
}
