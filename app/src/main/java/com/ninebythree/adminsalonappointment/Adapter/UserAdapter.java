package com.ninebythree.adminsalonappointment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninebythree.adminsalonappointment.Model.UsersModel;
import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.StylistViewHolder> {
    public final MyInterface myInterfaces;

    private List<UsersModel> stylistList;
    private Context context;

    public UserAdapter(List<UsersModel> stylistList, Context context, MyInterface myInterfaces) {
        this.stylistList = stylistList;
        this.context = context;
        this.myInterfaces = myInterfaces;
    }

    @NonNull
    @Override
    public StylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new StylistViewHolder(view, myInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistViewHolder holder, int position) {
        UsersModel usersModel = stylistList.get(position);

        holder.txtName.setText(usersModel.getName());
        holder.txtNumber.setText(usersModel.getContactNumber());
        holder.txtEmail.setText(usersModel.getEmail());
        holder.txtAddress.setText("Address:" + usersModel.getAddress());

    }

    @Override
    public int getItemCount() {
        return stylistList.size();
    }

    public class StylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtNumber, txtEmail, txtAddress;

        public StylistViewHolder(@NonNull View itemView, MyInterface myInterfaces) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtAddress = itemView.findViewById(R.id.txtAddress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myInterfaces != null ){
                int pos = getAdapterPosition();
                if(pos!= RecyclerView.NO_POSITION){
                    myInterfaces.onItemClick(pos, "usersModel");
                }

            }
        }
    }
}
