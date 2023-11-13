package com.ninebythree.adminsalonappointment.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ninebythree.adminsalonappointment.Adapter.MyInterface;
import com.ninebythree.adminsalonappointment.Adapter.ScheduleAdapter;
import com.ninebythree.adminsalonappointment.Model.ScheduleModel;
import com.ninebythree.adminsalonappointment.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentAppointment extends Fragment implements MyInterface {
    View view;

    private MaterialButton btnUpcoming, btnComplete, btnCancel;
    private RecyclerView recyclerView;
    private List<ScheduleModel> scheduleModelList = new ArrayList<>();
    private List<ScheduleModel> filter = new ArrayList<>();

    private ScheduleAdapter scheduleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appointment, container, false);

        btnUpcoming = view.findViewById(R.id.btnUpcoming);
        btnComplete = view.findViewById(R.id.btnComplete);
        btnCancel = view.findViewById(R.id.btnCancel);
        recyclerView = view.findViewById(R.id.recyclerView);

        scheduleAdapter = new ScheduleAdapter(filter, getContext(), this);

        //Set Up Data
        Data();

        //current data
        filter("upcoming");

        // Set onClickListeners for each button
        btnUpcoming.setOnClickListener(view1 -> {
               onButtonClicked(btnUpcoming);
               filter("upcoming");

           }
        );

        btnComplete.setOnClickListener(view1 -> {
               onButtonClicked(btnComplete);
               filter("complete");
           }
        );


        btnCancel.setOnClickListener(view1 -> {
            onButtonClicked(btnCancel);
            filter("cancelled");
        });


        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return  view;
    }

    private void filter(String status) {
        filter.clear();
        for (int i = 0; i < scheduleModelList.size(); i++) {
            if (scheduleModelList.get(i).getStatus().equals(status)) {
                filter.add(scheduleModelList.get(i));
            }
        }
        scheduleAdapter.notifyDataSetChanged();
    }

    private void Data() {
        scheduleModelList.add(new ScheduleModel(R.drawable.christian, "Christian Baustista", "Hair Cut", "Nov 20, 2021", "10:00 PM", "upcoming"));
        scheduleModelList.add(new ScheduleModel(R.drawable.angelika, "Angelika Panganiban", "Hair Rebond", "Jun 20, 2021", "8:00 AM", "complete"));
        scheduleModelList.add(new ScheduleModel(R.drawable.anna, "Anna Dela Cruz", "Hair Rebond", "Jan 16, 2021", "9:00 AM", "complete"));
        scheduleModelList.add(new ScheduleModel(R.drawable.gigi, "Gigi Delos Santos", "Nail Polish", "Aug 2, 2021", "10:00 AM", "upcoming"));
        scheduleModelList.add(new ScheduleModel(R.drawable.mark, "Mark Delos Reyes", "Hair Cut", "June 10, 2021", "11:00 AM", "cancelled"));
    }


    private void onButtonClicked(MaterialButton clickedButton) {
        // Reset all buttons to inactive state
        resetButtons();

        // Set the clicked button to active state
        clickedButton.setEnabled(false);
        // active button logic here
        clickedButton.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        clickedButton.setTextColor(getResources().getColorStateList(R.color.white));

        ColorStateList colorText = getResources().getColorStateList(R.color.black);
        ColorStateList defaultBackgroundColor = getResources().getColorStateList(R.color.surface);

        // Reset text color and background tint for other buttons
        if (clickedButton != btnUpcoming) {
            btnUpcoming.setBackgroundTintList(defaultBackgroundColor);
            btnUpcoming.setTextColor(colorText);

        }

        if (clickedButton != btnComplete) {
            btnComplete.setBackgroundTintList(defaultBackgroundColor);
            btnComplete.setTextColor(colorText);
        }

        if (clickedButton != btnCancel) {
            btnCancel.setBackgroundTintList(defaultBackgroundColor);
            btnCancel.setTextColor(colorText);

        }
    }

    private void resetButtons() {
        btnUpcoming = view.findViewById(R.id.btnUpcoming);
        btnComplete = view.findViewById(R.id.btnComplete);
        btnCancel = view.findViewById(R.id.btnCancel);

        // Enable all buttons
        btnUpcoming.setEnabled(true);
        btnComplete.setEnabled(true);
        btnCancel.setEnabled(true);
    }

    @Override
    public void onItemClick(int pos, String schedule) {

    }
}