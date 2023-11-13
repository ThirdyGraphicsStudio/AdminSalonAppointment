package com.ninebythree.adminsalonappointment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ninebythree.adminsalonappointment.Authentication.Loading;
import com.ninebythree.adminsalonappointment.R;


public class FragmentProfile extends Fragment {
    View view;

    private MaterialButton btnLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);

        //logout
        btnLogout.setOnClickListener(v -> logout());


        return  view;
    }

    private void logout() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(getContext(), Loading.class));
    }
}