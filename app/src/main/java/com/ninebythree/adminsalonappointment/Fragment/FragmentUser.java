package com.ninebythree.adminsalonappointment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ninebythree.adminsalonappointment.Activity.AddStylist;
import com.ninebythree.adminsalonappointment.Activity.StylistDetails;
import com.ninebythree.adminsalonappointment.Adapter.MyInterface;
import com.ninebythree.adminsalonappointment.Adapter.UserAdapter;
import com.ninebythree.adminsalonappointment.Model.UsersModel;
import com.ninebythree.adminsalonappointment.Model.UsersModel;
import com.ninebythree.adminsalonappointment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentUser extends Fragment implements MyInterface {
    View view;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ImageView add;
    List<UsersModel> userMModels = new ArrayList<>();
    public FragmentUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        add = view.findViewById(R.id.add);

        add.setOnClickListener(v -> startActivity(new Intent(getContext(), AddStylist.class)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(userMModels, getContext(), this);

        // Sample data
        getSampleStylistData();
        recyclerView.setAdapter(userAdapter);

        return view;
    }


    private void getSampleStylistData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> userDataMap = (Map<String, Object>) document.get("UserData");
                            if (userDataMap != null) {
                                // Now you have the UserData map for this document
                                // You can access its fields like this:
                                String name = (String) userDataMap.get("firstName") + " " + userDataMap.get("lastName");
                                String address = (String) userDataMap.get("address");
                                String contactNumber = (String) userDataMap.get("contactNumber");
                                String email = (String) userDataMap.get("email");

                                userMModels.add(new UsersModel(document.getId(), name, address, contactNumber, email));

                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the error
                        Log.d("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }


    @Override
    public void onItemClick(int pos, String schedule) {

    }
}

