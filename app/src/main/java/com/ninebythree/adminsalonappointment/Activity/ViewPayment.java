package com.ninebythree.adminsalonappointment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

public class ViewPayment extends AppCompatActivity {
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> {
            finish();
        });

        ImageView imageReceipt = findViewById(R.id.gcashQrCode);

        Intent intent = getIntent();
        if (intent!=null){
             image = intent.getStringExtra("image");
        }

        Picasso.get().load(image).placeholder(R.drawable.layer_logo).error(R.drawable.layer_logo).into(imageReceipt);


    }

}