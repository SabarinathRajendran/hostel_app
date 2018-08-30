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

public class hostel_search_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mvalue_field;
    private Button msearch_btn;
    private String value;
    private Spinner myear_spinner;
    private String year;
    private String mfieldval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_search_activity);

        mvalue_field = findViewById(R.id.hs_value_field);
        msearch_btn = findViewById(R.id.hs_search_btn);

        myear_spinner = (Spinner) findViewById(R.id.hs_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.profile_search_pick,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myear_spinner.setAdapter(adapter);
        myear_spinner.setOnItemSelectedListener(this);

        final Bundle mbundle = new Bundle();
        msearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = mvalue_field.getText().toString();

                switch(year){

                    case "Student_Name": mfieldval = "name";
                    break;
                    case "Student_Rollno": mfieldval = "rno";
                    break;
                    case "Tutor_Name": mfieldval = "tutorname";
                    break;
                    case "Department": mfieldval = "branch";
                    break;
                    default: mfieldval = "rno";
                        Toast.makeText(hostel_search_activity.this, "error", Toast.LENGTH_SHORT).show();

                }

                Intent resultintent = new Intent(hostel_search_activity.this, hostel_result_activity.class);
                mbundle.putString("sv",value);
                mbundle.putString("field",mfieldval);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        year = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "selected is" +year, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
