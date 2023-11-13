package com.ninebythree.adminsalonappointment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ninebythree.adminsalonappointment.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        // Find the SignIn button by its id
        MaterialButton btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SignInActivity
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });

        // Find the CreateAccount button by its id

    }
}