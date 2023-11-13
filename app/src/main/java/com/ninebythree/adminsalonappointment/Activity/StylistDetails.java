package com.ninebythree.adminsalonappointment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ninebythree.adminsalonappointment.R;

public class StylistDetails extends AppCompatActivity {

    ImageView imgBack;
    MaterialButton btnAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_details);

        imgBack = findViewById(R.id.imgBack);
        btnAppointment = findViewById(R.id.btnAppointment);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        btnAppointment.setOnClickListener(v -> {
            startActivity(new Intent(StylistDetails.this, EditStylist.class));
        });


    }
}