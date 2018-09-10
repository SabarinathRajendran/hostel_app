package com.example.sabari.hostel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class hostel_search_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mvalue_field;
    private Button msearch_btn;
    private String value;
    private Spinner myear_spinner;
    private String year;
    private String newyear;
    private String mfieldval;
    private Spinner mvaluespinner;

    private DatabaseReference mdatabase;
    private DatabaseReference searchdb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_search_activity);

        mvalue_field = findViewById(R.id.hs_value_field);
        msearch_btn = findViewById(R.id.hs_search_btn);

        myear_spinner = (Spinner) findViewById(R.id.hs_spinner);
        mvaluespinner = findViewById(R.id.hs_value_spinner);

        //firebase init
        mdatabase = FirebaseDatabase.getInstance().getReference().child("tosearch");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.profile_search_pick,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myear_spinner.setAdapter(adapter);
        myear_spinner.setOnItemSelectedListener(this);

        final Bundle mbundle = new Bundle();
        msearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(year){

                    case "Student_Name": mfieldval = "name";
                        value = mvalue_field.getText().toString().toUpperCase();
                    break;
                    case "Student_Rollno": mfieldval = "rno";
                        value = mvalue_field.getText().toString().toUpperCase();
                    break;
                    case "Tutor_Name": mfieldval = "tutorname";
                    value = mvaluespinner.getSelectedItem().toString().toUpperCase();
                    break;
                    case "Department": mfieldval = "branch";
                        value = mvaluespinner.getSelectedItem().toString().toUpperCase();
                    break;
                    default: mfieldval = "rno";
                        Toast.makeText(hostel_search_activity.this, "error", Toast.LENGTH_SHORT).show();

                }

                Toast.makeText(hostel_search_activity.this, "selected is "+ mfieldval+"value is "+value, Toast.LENGTH_SHORT).show();

                Intent resultintent = new Intent(hostel_search_activity.this, hostel_result_activity.class);
                mbundle.putString("sv",value);
                mbundle.putString("field",mfieldval);
                resultintent.putExtras(mbundle);
                startActivity(resultintent);

                }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner selected = (Spinner) adapterView;
        if (selected.getId() == R.id.hs_spinner)
        {
            year = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "selected is" + year, Toast.LENGTH_SHORT).show();
            setvisibilty(year);
        }
        else if (selected.getId() == R.id.hs_value_spinner){
            newyear = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, "selected is" + newyear, Toast.LENGTH_SHORT).show();
        }
    }

    private void setvisibilty(String year) {

        switch(year){

            case "Student_Name": mvalue_field.setVisibility(View.VISIBLE);
            mvaluespinner.setVisibility(View.GONE);
                break;
            case "Student_Rollno": mvalue_field.setVisibility(View.VISIBLE);
                mvaluespinner.setVisibility(View.GONE);
                break;
            case "Tutor_Name": mvaluespinner.setVisibility(View.VISIBLE);
                mvalue_field.setVisibility(View.GONE);
                populatespinner(year);
                break;
            case "Department": mvaluespinner.setVisibility(View.VISIBLE);
                mvalue_field.setVisibility(View.GONE);
                populatespinner(year);
                break;
            default: mvalue_field.setVisibility(View.VISIBLE);
                mvaluespinner.setVisibility(View.GONE);


        }
    }

    private void populatespinner(String year) {



        switch (year)
        {
            case "Tutor_Name" : searchdb = mdatabase.child("tutorname");
            retiveadapter(searchdb);
            break;
            case "Department" : searchdb = mdatabase.child("department");
                retiveadapter(searchdb);
            break;
            default:searchdb = mdatabase.child("error");
                retiveadapter(searchdb);
        }

    }

    public ArrayList<String> retrieve()
    {
        final ArrayList<String> spacecrafts=new ArrayList<>();

        searchdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,spacecrafts);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,spacecrafts);

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

        return spacecrafts;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> spacecrafts)
    {
        spacecrafts.clear();
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String name=ds.getValue(String.class);
            spacecrafts.add(name);
        }

    }


    private void retiveadapter(DatabaseReference searchdb) {

        final ArrayList<String> mnewadapter = new ArrayList<>();

        mnewadapter.add("Select any One");
        mvaluespinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, mnewadapter));
        mvaluespinner.setOnItemSelectedListener(this);

        searchdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mnewadapter.add(dataSnapshot.getValue(String.class));

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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
