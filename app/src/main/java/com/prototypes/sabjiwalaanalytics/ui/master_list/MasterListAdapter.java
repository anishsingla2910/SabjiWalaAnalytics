package com.prototypes.sabjiwalaanalytics.ui.master_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prototypes.sabjiwalaanalytics.R;
import com.prototypes.sabjiwalaanalytics.classes.Vegetable;

import java.util.ArrayList;
import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {
	
	ArrayList<Vegetable> vegetables;
	ArrayAdapter<String> adapter;
	
	public MasterListAdapter(ArrayList<Vegetable> vegetables){
		this.vegetables = vegetables;
	}
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_item, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.item_name.setText(vegetables.get(position).getName());
		holder.item_type.setText(vegetables.get(position).getType());
		holder.item_category.setText(vegetables.get(position).getCategory());
		holder.item_price.setText(vegetables.get(position).getPrice());
		StorageReference itemImage = FirebaseStorage.getInstance().getReference().child("MasterList/" + vegetables.get(position).getName() + vegetables.get(position).getType() + ".png");
		Glide.with(holder.item_image)
				.load(itemImage)
				.error(R.drawable.vegetable_image_not_available)
				.into(holder.item_image);
		List<String> qty = vegetables.get(position).getQuantity();
		adapter = new ArrayAdapter<>(holder.quantity.getContext(), R.layout.spinner_layout, R.id.text, qty);
		holder.quantity.setAdapter(adapter);
	}
	
	@Override
	public int getItemCount() {
		return vegetables.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder {
		
		ImageView item_image;
		TextView item_name,item_category,item_type,item_price;
		Spinner quantity;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			
			quantity = itemView.findViewById(R.id.spinner);
			item_image = itemView.findViewById(R.id.item_image);
			item_category = itemView.findViewById(R.id.item_category);
			item_name = itemView.findViewById(R.id.item_name);
			item_price = itemView.findViewById(R.id.item_price);
			item_type = itemView.findViewById(R.id.item_type);
		}
	}
}
