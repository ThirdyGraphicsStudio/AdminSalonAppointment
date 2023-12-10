package com.ninebythree.adminsalonappointment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

public class StylistDetails extends AppCompatActivity {

    ImageView imgBack, imgProfile;
    MaterialButton btnAppointment;
    String stylistName, stylistSpecialization, stylistRateAverage, stylistRateCount, stylistExperience, stylistImage, stylistDescription,id;


    private TextView txtExperience, txtRateCount, txtRatingAverage, txtDescription,txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_details);

        imgBack = findViewById(R.id.imgBack);
        btnAppointment = findViewById(R.id.btnAppointment);
        imgProfile = findViewById(R.id.imgProfile);
        txtExperience = findViewById(R.id.txtExperience);
        txtRateCount = findViewById(R.id.txtRateCount);
        txtRatingAverage = findViewById(R.id.txtRatingAverage);
        txtDescription = findViewById(R.id.txtDescription);
        txtName = findViewById(R.id.txtName);



        imgBack.setOnClickListener(v -> {
            finish();
        });


        Intent intent = getIntent();
        if (intent != null) {

            id = intent.getStringExtra("id");
             stylistName = intent.getStringExtra("name");
             stylistSpecialization = intent.getStringExtra("specialization");
             stylistRateAverage = String.valueOf(intent.getFloatExtra("stylistRateAverage", 0f));
             stylistRateCount = String.valueOf(intent.getIntExtra("rateCount", 0));
             stylistExperience = intent.getStringExtra("experience");
             stylistImage = intent.getStringExtra("image");
             stylistDescription = intent.getStringExtra("description");

            txtName.setText(stylistName);
            txtDescription.setText(stylistDescription);
            txtRatingAverage.setText(stylistRateAverage);
            txtRateCount.setText(stylistRateCount);
            txtExperience.setText(stylistExperience + " Years");

            Picasso.get().load(stylistImage).placeholder(R.drawable.profile).error(R.drawable.profile).into(imgProfile);

        }


        btnAppointment.setOnClickListener(v -> {
            Intent intent1 = new Intent(StylistDetails.this, EditStylist.class);
            intent1.putExtra("name", stylistName);
            intent1.putExtra("specialization", stylistSpecialization);
            intent1.putExtra("experience", stylistExperience);
            intent1.putExtra("image", stylistImage);
            intent1.putExtra("description", stylistDescription);
            intent1.putExtra("id", id);
            startActivity(intent1);

        });


    }
}