package com.ninebythree.adminsalonappointment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ninebythree.adminsalonappointment.MainActivity;
import com.ninebythree.adminsalonappointment.R;


public class Login extends AppCompatActivity {

    // Declare variables
    private EditText edtEmail, editPassword;
    private Button btnLogin;

    private TextView btnForgotPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail);
        editPassword = findViewById(R.id.editPassword);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);



        // Set click listener for btnLogin Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Perform error handling for edtEmail and editPassword
                String email = edtEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editPassword.setError("Password is required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Sign in with Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Successfully signed in
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Check the role in Firestore
                                    checkUserRole(user.getUid());
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    // Sign in failed
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(e -> {
                            // Log error for debugging
                            progressBar.setVisibility(View.GONE);
                            Log.e("LoginActivity", "Sign in failed", e);
                            // Display a toast message for the user
                            // Toast.makeText(Login.this, "Sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            }
        });

        // Set click listener for btnForgotPassword (Coming Soon)
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a toast message indicating that it's coming soon
                Toast.makeText(Login.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Function to check the role in Firestore
    private void checkUserRole(String userId) {
        // Initialize Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Reference to the user document in the "Authentication" collection
        DocumentReference userRef = db.collection("Authentication").document(userId);

        // Retrieve user document from Firestore
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the user role from the document
                        String role = document.getString("role");

                        if (role != null && role.equals("admin")) {
                            // Role is "users", navigate to MainActivity
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Invalid user, display a toast message
                            Toast.makeText(Login.this, "Invalid user", Toast.LENGTH_SHORT).show();
                            // Sign out the user
                            mAuth.signOut();
                        }
                    } else {
                        // Invalid user, display a toast message
                        Toast.makeText(Login.this, "User Not Existed! Contact Admin", Toast.LENGTH_SHORT).show();
                        // Sign out the user
                        mAuth.signOut();
                    }
                } else {
                    // Error occurred while fetching the document, display a toast message with the error
                    Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
