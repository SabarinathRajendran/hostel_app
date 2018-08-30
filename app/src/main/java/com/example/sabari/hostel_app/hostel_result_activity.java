package com.example.sabari.hostel_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class hostel_result_activity extends AppCompatActivity {

    private DatabaseReference mfirebase;

    private RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_result_activity);

       Bundle b = getIntent().getExtras();

       String searchkey = b.getString("sv");

        mfirebase = FirebaseDatabase.getInstance().getReference().child("profile");
        mRecyclerview= findViewById(R.id.hr_recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        Query query = mfirebase.orderByChild("rno").startAt(searchkey).endAt(searchkey + "\uf8ff");

        FirebaseRecyclerAdapter<hostel_students,cviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<hostel_students, cviewHolder>(
                hostel_students.class,
                R.layout.hostel_result_view,
                cviewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(cviewHolder viewHolder, hostel_students model, int position) {

                viewHolder.setDetails(getApplicationContext(),model.getName(),model.getRno());

            }
        };

        mRecyclerview.setAdapter(firebaseRecyclerAdapter);


    }

    public static class cviewHolder extends RecyclerView.ViewHolder{

        View mview;

        public cviewHolder(View itemView) {
            super(itemView);
            mview = itemView;

        }

        public void setDetails(Context ctx,String name, String rno){
            TextView mname = mview.findViewById(R.id.hsv_name);
            TextView mrno = mview.findViewById(R.id.hsv_rollno);
            TextView mnumber = mview.findViewById(R.id.hsv_number);

            mname.setText(name);
            mrno.setText(rno);
        }
    }
}
