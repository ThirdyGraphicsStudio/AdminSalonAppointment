package com.ninebythree.adminsalonappointment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ninebythree.adminsalonappointment.Model.StylistModel;
import com.ninebythree.adminsalonappointment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StylistAdapter extends RecyclerView.Adapter<StylistAdapter.StylistViewHolder> {
    public final MyInterface myInterfaces;

    private List<StylistModel> stylistList;
    private Context context;

    public StylistAdapter(List<StylistModel> stylistList, Context context, MyInterface myInterfaces) {
        this.stylistList = stylistList;
        this.context = context;
        this.myInterfaces = myInterfaces;
    }

    @NonNull
    @Override
    public StylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stylist, parent, false);
        return new StylistViewHolder(view, myInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistViewHolder holder, int position) {
        StylistModel stylist = stylistList.get(position);

        holder.nameTextView.setText(stylist.getName());
        holder.specialtyTextView.setText(stylist.getSpecialty());
        holder.ratingTextView.setText(String.valueOf(stylist.getAverageRating()));
        holder.reviewsTextView.setText(String.format("Reviews (%d)", stylist.getReviews()));

        Picasso.get().load(stylist.getImageResource()).placeholder(R.drawable.profile).error(R.drawable.profile).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return stylistList.size();
    }

    public class StylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView nameTextView, specialtyTextView, ratingTextView, reviewsTextView;

        public StylistViewHolder(@NonNull View itemView, MyInterface myInterfaces) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            specialtyTextView = itemView.findViewById(R.id.specialty);
            ratingTextView = itemView.findViewById(R.id.average_rating);
            reviewsTextView = itemView.findViewById(R.id.reviews);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myInterfaces != null ){
                int pos = getAdapterPosition();
                if(pos!= RecyclerView.NO_POSITION){
                    myInterfaces.onItemClick(pos, "stylist");
                }

            }
        }
    }
}
