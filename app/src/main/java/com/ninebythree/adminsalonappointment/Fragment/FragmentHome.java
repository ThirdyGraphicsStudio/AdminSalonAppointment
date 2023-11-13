package com.ninebythree.adminsalonappointment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        add.setOnClickListener(v -> startActivity(new Intent(getContext(), EditStylist.class)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        List<StylistModel> stylistList = getSampleStylistData();

        stylistAdapter = new StylistAdapter(stylistList, getContext(), this);
        recyclerView.setAdapter(stylistAdapter);

        return view;
    }


    private List<StylistModel> getSampleStylistData() {
        List<StylistModel> stylistList = new ArrayList<>();
        stylistList.add(new StylistModel(R.drawable.angelika, "Angela Kang", "Hair Rebond", 4.5f, 240));
        stylistList.add(new StylistModel(R.drawable.anna, "Anne Dela Cruz", "Anne Dela Cruz", 4.2f, 420));
        stylistList.add(new StylistModel(R.drawable.gigi, "Gigi Salamanca", "Hair Colorist", 4.8f, 10));
        stylistList.add(new StylistModel(R.drawable.mark, "Mark Dela Cruz", "Haircut", 4.9f, 120));
        stylistList.add(new StylistModel(R.drawable.christian, "Christian Baustista", "Hair Rebond", 5.0f, 30));



        // Add more stylist data as needed
        return stylistList;
    }

    @Override
    public void onItemClick(int pos, String schedule) {
        Intent intent = new Intent(getContext(), StylistDetails.class);
        startActivity(intent);

    }
}