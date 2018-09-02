package com.example.sabari.hostel_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.sabari.hostel_app.login.isValidEmail;

public class forgot_password extends AppCompatActivity {

    private Button mreset;
    private EditText medittext;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;
    private ImageView mbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mreset = findViewById(R.id.fp_submit_btn);
        medittext = findViewById(R.id.fp_email_field);
        mAuth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(this);
        mbackbtn = findViewById(R.id.fp_backbutton);

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgot_password.this, login.class));
            }
        });

        final Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error_outline_red_500_18dp);
        customErrorDrawable.setBounds(-10, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        mreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = medittext.getText().toString();

                if (isValidEmail(email)){

                        validaiton(email);

                }else{
                    medittext.setError("this is not a valid Email",customErrorDrawable);
                }


            }
        });


    }

    private void validaiton(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mprogress.setMessage("Email has been sent successfully please check your Email");
                    mprogress.show();
                }
                else
                {
                    mprogress.setMessage("error occurred please check network or contact the developers ");
                    mprogress.show();
                }
            }
        });
    }
}
