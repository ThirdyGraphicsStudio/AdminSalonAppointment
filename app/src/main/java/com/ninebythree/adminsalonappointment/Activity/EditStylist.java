package com.ninebythree.adminsalonappointment.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ninebythree.adminsalonappointment.MainActivity;
import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EditStylist extends AppCompatActivity {

    private TextView btnUpload;
    private ImageView imgProfile;
    private TextInputEditText edtStylistName,edtSpecialization,edtExperience,editStylist;
    private MaterialButton btnSave;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE = 1;
    private Uri selectedImage = null;
    private ProgressBar progressBar;
    String stylistName, specialization, experience, description, image, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stylist);

        btnUpload = findViewById(R.id.btnUpload);
        imgProfile = findViewById(R.id.imgProfile);
        edtStylistName = findViewById(R.id.edtStylistName);
        edtSpecialization = findViewById(R.id.edtSpecialization);
        edtExperience = findViewById(R.id.edtExperience);
        editStylist = findViewById(R.id.editStylist);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);

        btnUpload.setOnClickListener(v -> pickImage());

        btnSave.setOnClickListener(view ->   editStylist());

        Intent intent = getIntent();
        if (intent != null) {

            stylistName = intent.getStringExtra("name");
            specialization = intent.getStringExtra("specialization");
            experience = intent.getStringExtra("experience");
            description = intent.getStringExtra("description");
            image = intent.getStringExtra("image");
            id = intent.getStringExtra("id");

            edtStylistName.setText(stylistName);
            edtSpecialization.setText(specialization);
            edtExperience.setText(experience);
            editStylist.setText(description);


            Picasso.get().load(image).placeholder(R.drawable.profile).into(imgProfile);
        }

    }

    private void editStylist() {
        String newStylistName = edtStylistName.getText().toString().trim();
        String newSpecialization = edtSpecialization.getText().toString().trim();
        String newExperience = edtExperience.getText().toString().trim();
        String newDescription = editStylist.getText().toString().trim();

        Map<String, Object> updatedValues = new HashMap<>();
        if (!stylistName.equals(newStylistName)) {
            updatedValues.put("stylistName", newStylistName);
        }
        if (!specialization.equals(newSpecialization)) {
            updatedValues.put("specialization", newSpecialization);
        }
        if (!experience.equals(newExperience)) {
            updatedValues.put("experience", newExperience);
        }
        if (!description.equals(newDescription)) {
            updatedValues.put("description", newDescription);
        }

        // If a new image was selected, upload it. Otherwise, update the Firestore document directly.
        if (selectedImage != null) {
            // Create a reference to the new image file
            StorageReference reference = FirebaseStorage.getInstance().getReference()
                    .child("stylist_images")
                    .child(newStylistName + "_" + System.currentTimeMillis() + ".jpg");

            // Upload the new image to Firebase Storage
            reference.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();

                // Add the new image URL to the update map
                updatedValues.put("image", downloadUri.toString());

                // Update Firestore document
                updateFirestoreDocument(updatedValues);
            }).addOnFailureListener(e -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditStylist.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }).addOnProgressListener(snapshot -> {
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
            });
        } else {
            // If no new image was selected, just update the document
            updateFirestoreDocument(updatedValues);
        }
    }

    private void updateFirestoreDocument(Map<String, Object> updatedValues) {
        if (!updatedValues.isEmpty()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference stylistDetailsRef = db.collection("stylists").document(id);

            stylistDetailsRef.update(updatedValues)
                    .addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditStylist.this, "Stylist updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditStylist.this, "Failed to update stylist", Toast.LENGTH_SHORT).show();
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No changes to update", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveStylist() {
        String stylistName = edtStylistName.getText().toString().trim();
        String specialization = edtSpecialization.getText().toString().trim();
        String experience = edtExperience.getText().toString().trim();
        String stylist = editStylist.getText().toString().trim();

        if (selectedImage == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (stylistName.isEmpty()) {
            edtStylistName.setError("Stylist name is required");
            edtStylistName.requestFocus();
            return;
        }

        if (specialization.isEmpty()) {
            edtSpecialization.setError("Specialization is required");
            edtSpecialization.requestFocus();
            return;
        }

        if (experience.isEmpty()) {
            edtExperience.setError("Experience is required");
            edtExperience.requestFocus();
            return;
        }

        if (stylist.isEmpty()) {
            editStylist.setError("Stylist is required");
            editStylist.requestFocus();
            return;
        }

        if (selectedImage == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        StorageReference reference = FirebaseStorage.getInstance().getReference().child("stylist_images").child(stylistName + "_" + System.currentTimeMillis() + ".jpg");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        reference.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful());
            Uri downloadUri = uriTask.getResult();

            HashMap<String, Object> newStylistModel = new HashMap<>();
            newStylistModel.put("stylistName", stylistName);
            newStylistModel.put("specialization", specialization);
            newStylistModel.put("experience", experience);
            newStylistModel.put("stylist", stylist);
            newStylistModel.put("image", downloadUri.toString());
            newStylistModel.put("rateAverage", 0.0f);
            newStylistModel.put("rateCount", 0);


            CollectionReference stylistDetailsRef = db.collection("stylists");

//            stylistDetailsRef.get().addOnSuccessListener(documentSnapshot -> {
//                ArrayList<HashMap<String, Object>> stylistDetails;
//                if (documentSnapshot.exists() && documentSnapshot.getData().get("stylistDetails") != null) {
//                    stylistDetails = (ArrayList<HashMap<String, Object>>) documentSnapshot.getData().get("stylistDetails");
//                } else {
//                    stylistDetails = new ArrayList<>();
//                }

//                stylistDetails.add(newStylistModel);

            stylistDetailsRef.add(newStylistModel)
                    .addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditStylist.this, "Stylist added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditStylist.this, "Failed to add stylist", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onFailure: " + e.toString());
                    });
        }).addOnFailureListener(e -> {
            // Handle the error
            progressBar.setVisibility(View.GONE);
            Toast.makeText(EditStylist.this, "Failed to add stylist", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onFailure: " + e.toString());
        }).addOnProgressListener(snapshot -> {
            // Observe state change events such as progress, pause, and resume
            // ...
            progressBar.setVisibility(View.VISIBLE);
        });

    }




    private void pickImage() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // Permission has already been granted
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    // Permission denied. Handle the case where the user denies the permission.
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            imgProfile.setImageURI(selectedImage);
        }

    }

}