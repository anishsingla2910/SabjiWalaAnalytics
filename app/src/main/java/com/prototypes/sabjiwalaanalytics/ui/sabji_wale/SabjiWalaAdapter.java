package com.prototypes.sabjiwalaanalytics.ui.sabji_wale;

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
import com.prototypes.sabjiwalaanalytics.classes.Shop;

import java.util.ArrayList;

public class SabjiWalaAdapter extends RecyclerView.Adapter<SabjiWalaAdapter.ViewHolder> {
    
    ArrayList<Shop> shops;
    
    public SabjiWalaAdapter(ArrayList<Shop> shops){
        this.shops = shops;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sabji_wala_layout, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shop_name.setText(shops.get(position).getShopName());
        holder.owner_name.setText(shops.get(position).getOwnerName());
        holder.shop_address.setText(shops.get(position).getShopAddress());
        StorageReference shopImage = FirebaseStorage.getInstance().getReference().child("shoppictures/" + shops.get(position).getId());
        StorageReference ownerImage = FirebaseStorage.getInstance().getReference().child("shop_owner_pictures/" + shops.get(position).getId());
        Glide.with(holder.shop_image)
                .load(shopImage)
                .error(R.drawable.shop_image_not_available)
                .into(holder.shop_image);
        Glide.with(holder.owner_image)
                .load(ownerImage)
                .error(R.drawable.default_profile)
                .into(holder.owner_image);
    }
    
    @Override
    public int getItemCount() {
        return shops.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
    
        TextView shop_name, owner_name, shop_address;
        ImageView shop_image;
        ImageView owner_image;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            owner_name = itemView.findViewById(R.id.owner_name);
            shop_address = itemView.findViewById(R.id.shop_address);
            shop_image = itemView.findViewById(R.id.shop_image);
            owner_image = itemView.findViewById(R.id.owner_image);
        }
    }
}
