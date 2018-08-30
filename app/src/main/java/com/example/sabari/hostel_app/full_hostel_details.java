package com.example.sabari.hostel_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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

    private ArrayList<Item> animalList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_hostel_details);

        mlistview = findViewById(R.id.fh_listview);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("profile").child("15BCE015");


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

        /*mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String skey = dataSnapshot.getKey();
                String svalue = dataSnapshot.getValue(String.class);

                mkeyarray.add(new Item(skey,svalue));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
