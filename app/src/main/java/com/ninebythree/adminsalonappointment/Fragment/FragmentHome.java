package com.ninebythree.adminsalonappointment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ninebythree.adminsalonappointment.Activity.AddStylist;
import com.ninebythree.adminsalonappointment.Activity.EditStylist;
import com.ninebythree.adminsalonappointment.Activity.StylistDetails;
import com.ninebythree.adminsalonappointment.Adapter.MyInterface;
import com.ninebythree.adminsalonappointment.Adapter.StylistAdapter;
import com.ninebythree.adminsalonappointment.Model.StylistModel;
import com.ninebythree.adminsalonappointment.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements MyInterface {
    View view;

    private RecyclerView recyclerView;
    private StylistAdapter stylistAdapter;
    private ImageView add;
    List<StylistModel> stylistLists = new ArrayList<>();
    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        add = view.findViewById(R.id.add);

        add.setOnClickListener(v -> startActivity(new Intent(getContext(), AddStylist.class)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stylistAdapter = new StylistAdapter(stylistLists, getContext(), this);

        // Sample data
        getSampleStylistData();
        recyclerView.setAdapter(stylistAdapter);

        return view;
    }


    private void getSampleStylistData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("stylists")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Stylist stylist = document.toObject(Stylist.class);
                            stylistLists.add(new StylistModel(document.getId().toString(), stylist.getImage(), stylist.getStylistName(), stylist.getSpecialization(), stylist.getRateAverage(), stylist.getRateCount(), stylist.getExperience(), stylist.getStylist()));
                        }
                        stylistAdapter.notifyDataSetChanged();
                        // Now 'stylistList' contains all the stylists from Firestore
                        // Do something with your list, like updating the UI
                    } else {
                        // Handle the error
                        Log.d("Firestore", "Error getting documents: ", task.getException());
                    }
                });


    }


    @Override
    public void onItemClick(int pos, String schedule) {
        Intent intent = new Intent(getContext(), StylistDetails.class);
        intent.putExtra("id", stylistLists.get(pos).getId());
        intent.putExtra("image", stylistLists.get(pos).getImageResource());
        intent.putExtra("name", stylistLists.get(pos).getName());
        intent.putExtra("specialization", stylistLists.get(pos).getSpecialty());
        intent.putExtra("rateAverage", stylistLists.get(pos).getAverageRating());
        intent.putExtra("rateCount", stylistLists.get(pos).getReviews());
        intent.putExtra("experience", stylistLists.get(pos).getExperience());
        intent.putExtra("description", stylistLists.get(pos).getDescription());

        startActivity(intent);
//        Toast.makeText(getContext(), "Clicked" + stylistLists.get(pos).getId(), Toast.LENGTH_SHORT).show();

    }
}

class Stylist {
    // Assuming these are the fields in your Firestore documents
    private String stylistName;
    private String specialization;
    private int rateCount; // or double, depending on what you use
    private String experience; // or long, if you store experience as a number of milliseconds
    private float rateAverage;

    private String image;
    private String stylist;

    // Constructor, getters, and setters...


    public Stylist() {
    }

    public String getStylist() {
        return stylist;
    }

    public void setStylist(String stylist) {
        this.stylist = stylist;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public float getRateAverage() {
        return rateAverage;
    }

    public void setRateAverage(float rateAverage) {
        this.rateAverage = rateAverage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

