package com.example.sabari.hostel_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    private static final String TAG = "login";
    private EditText memail;
    private EditText mpassword;
    private Button msubmit;

    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        msubmit = findViewById(R.id.l_submit_button);
        memail = findViewById(R.id.l_email_field);
        mpassword = findViewById(R.id.l_password_field);

        mprogress= new ProgressDialog(this);

        final Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error_outline_red_500_18dp);
        customErrorDrawable.setBounds(-10, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());


        mAuth = FirebaseAuth.getInstance();
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprogress.setMessage("Signing In please wait, also check network");
                String email = memail.getText().toString();
                String password = mpassword.getText().toString();
                        if (isValidEmail(email)){
                            if (password.length()>5) {
                                validaiton(email,password);
                            }
                            else{
                                mpassword.setError("Passowrd should me more than 8 characters",customErrorDrawable);
                            }

                        }else{
                            memail.setError("this is not a valid Email",customErrorDrawable);
                        }

            }
        });

    }

    private void validaiton(String email, String password) {

        mprogress.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            Intent loginleintent = new Intent(login.this, MainActivity.class);
                            loginleintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            mprogress.dismiss();
                            startActivity(loginleintent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            mprogress.dismiss();
                            Toast.makeText(login.this, "Authentication failed.check network or enter correct details",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public boolean validatePassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
