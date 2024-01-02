package com.ninebythree.adminsalonappointment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import  com.ninebythree.adminsalonappointment.Activity.ViewPayment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ninebythree.adminsalonappointment.Model.ScheduleModel;
import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.StylistViewHolder> {
    public final MyInterface myInterfaces;

    private List<ScheduleModel> scheduleModels;
    private Context context;

    public ScheduleAdapter(List<ScheduleModel> scheduleModels, Context context, MyInterface myInterfaces) {
        this.scheduleModels = scheduleModels;
        this.context = context;
        this.myInterfaces = myInterfaces;
    }

    @NonNull
    @Override
    public StylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new StylistViewHolder(view, myInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistViewHolder holder, int position) {

        holder.txtName.setText(scheduleModels.get(position).getName());
        holder.txtSpecialty.setText(scheduleModels.get(position).getSpecialty());
        holder.txtDate.setText(scheduleModels.get(position).getDate());
        holder.txtTime.setText(scheduleModels.get(position).getTime());
        holder.txtClientName.setText("Client Name: " + scheduleModels.get(position).getClientName());
        holder.txtClientAddress.setText("Client Address: " + scheduleModels.get(position).getClientAddress());

        Picasso.get().load(scheduleModels.get(position).getImage()).placeholder(R.drawable.profile).error(R.drawable.profile).into(holder.imgProfile);

        if(scheduleModels.get(position).getStatus().equals("upcoming")){
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setBackgroundColor(context.getResources().getColor(R.color.highlight));
            holder.btnCancel.setText("Cancel");
            holder.btnCancel.setTextColor(context.getResources().getColor(R.color.black));
            holder.btnCancel.setEnabled(true);

            holder.btnCancel.setOnClickListener(view -> {
                // Update the status in your model
                scheduleModels.get(position).setStatus("cancelled");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Appointments").document(scheduleModels.get(position).getId()).update("status", "cancelled");
                // Update the button UI
                holder.btnCancel.setText("CANCELLED");
                holder.btnCancel.setEnabled(false);
                // Remove the item from your data source
                scheduleModels.remove(position);
                // Notify the adapter that an item is removed
                notifyItemRemoved(position);
                // to be reflected, like updating positions of subsequent items in the RecyclerView
                notifyItemRangeChanged(position, scheduleModels.size());
            });




        } else if(scheduleModels.get(position).getStatus().equals("complete")){
            holder.btnCancel.setVisibility(View.GONE);
        }
        else{
            holder.btnCancel.setBackgroundColor(context.getResources().getColor(R.color.gray));
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setText("CANCELLED");
            holder.btnCancel.setEnabled(false);
        }

        holder.btnViewScreenshot.setVisibility(View.GONE);

        if (scheduleModels.get(position).getGcashImage()!= null){
            holder.btnViewScreenshot.setVisibility(View.VISIBLE);
        }

        holder.btnViewScreenshot.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewPayment.class);
            intent.putExtra("image", scheduleModels.get(position).getGcashImage().toString());
            context.startActivity(intent);
        });


        if(scheduleModels.get(position).getPaymentMethod() != null && scheduleModels.get(position).getPaymentMethod().equals("Gcash")){
            holder.txtPaymentMethod.setText("Gcash Payment");
        }


    }

    @Override
    public int getItemCount() {
        return scheduleModels.size();
    }

    public class StylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgProfile;
        TextView txtName, txtSpecialty, txtDate, txtTime, txtClientName, txtClientAddress,txtPaymentMethod;
        MaterialButton btnCancel,btnViewScreenshot;

        public StylistViewHolder(@NonNull View itemView, MyInterface myInterfaces) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgProfile);
            txtName = itemView.findViewById(R.id.txtName);
            txtSpecialty = itemView.findViewById(R.id.txtSpecialty);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            txtClientName = itemView.findViewById(R.id.txtClientName);
            txtClientAddress = itemView.findViewById(R.id.txtClientAddress);
            txtPaymentMethod = itemView.findViewById(R.id.txtPaymentMethod);
            btnViewScreenshot = itemView.findViewById(R.id.btnViewScreenshot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myInterfaces != null ){
                int pos = getAdapterPosition();
                if(pos!= RecyclerView.NO_POSITION){
                    myInterfaces.onItemClick(pos, "schedule");
                }

            }
        }
    }
}
