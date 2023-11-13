package com.ninebythree.adminsalonappointment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ninebythree.adminsalonappointment.MainActivity;
import com.ninebythree.adminsalonappointment.R;


public class Loading extends AppCompatActivity {

    private static final int DELAY_DURATION = 2000; // 2 seconds
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Initialize Firebase
        FirebaseApp.initializeApp(getApplicationContext());
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Delay for 2 seconds and start the new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(currentUser != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), Welcome.class));
                }
                finish(); // Optional, if you want to close the current activity
            }
        }, DELAY_DURATION);


    }
}
