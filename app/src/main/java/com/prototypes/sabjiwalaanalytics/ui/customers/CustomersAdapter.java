package com.prototypes.sabjiwalaanalytics.ui.customers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prototypes.sabjiwalaanalytics.R;
import com.prototypes.sabjiwalaanalytics.classes.Customer;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> {
    
    ArrayList<Customer> customers;
    
    public CustomersAdapter(ArrayList<Customer> customers){
        this.customers = customers;
    }
    
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout, parent, false);
        return new MyViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.customer_name.setText(customers.get(position).getName());
        holder.customer_address.setText(customers.get(position).getAddress());
        StorageReference customerImage = FirebaseStorage.getInstance().getReference().child("customer_pictures/" + customers.get(position).getId());
        Glide.with(holder.customer_image)
                .load(customerImage)
                .error(R.drawable.default_profile)
                .into(holder.customer_image);
    }
    
    @Override
    public int getItemCount() {
        return customers.size();
    }
    
    class MyViewHolder extends RecyclerView.ViewHolder {
        
        ImageView customer_image;
        TextView customer_address, customer_name;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_address = itemView.findViewById(R.id.customer_address);
            customer_image = itemView.findViewById(R.id.customer_image);
        }
    }
}
