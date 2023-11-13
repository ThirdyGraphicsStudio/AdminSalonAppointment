package com.ninebythree.adminsalonappointment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ninebythree.adminsalonappointment.Fragment.FragmentAppointment;
import com.ninebythree.adminsalonappointment.Fragment.FragmentHome;
import com.ninebythree.adminsalonappointment.Fragment.FragmentProfile;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnItemSelectedListener(navListener);

        Fragment selectedFragment = new FragmentHome();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

    }


    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                int itemId = item.getItemId(); /* obtain the selected item ID from your source */
                Fragment selectedFragment = null;

                if (itemId == R.id.nav_home) {
                    selectedFragment = new FragmentHome();
                } else if (itemId == R.id.nav_appointment) {
                    selectedFragment = new FragmentAppointment();
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new FragmentProfile();
                }  else {
                    // Handle other cases or provide a default behavior
                    selectedFragment = new FragmentHome();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };



}