package com.prototypes.sabjiwalaanalytics.add_vegetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prototypes.sabjiwalaanalytics.R;
import com.prototypes.sabjiwalaanalytics.classes.CategoryHindi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddVegetableActivity extends AppCompatActivity {
	
	Toolbar toolbar;
	TextInputEditText vegetable_name, vegetable_type, vegetable_price, vegetable_name_hindi, vegetable_type_hindi;
	Spinner spinner_category;
	Button add_button;
	FirebaseFirestore fStore;
	String category;
	List<String> categories;
	ArrayAdapter<String> adapter;
	ArrayList<CategoryHindi> categoryHindis;
	Spinner quantity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vegetable);
		
		Intent intent = getIntent();
		category = intent.getStringExtra("category");
		
		vegetable_name = findViewById(R.id.vegetable_name);
		vegetable_type = findViewById(R.id.vegetable_type);
		vegetable_price = findViewById(R.id.vegetable_price);
		spinner_category = findViewById(R.id.spinner);
		vegetable_name_hindi = findViewById(R.id.vegetable_name_hindi);
		vegetable_type_hindi = findViewById(R.id.vegetable_type_hindi);
		quantity = findViewById(R.id.quantity);
		categoryHindis = new ArrayList<>();
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Add vegetable to Master List");
		toolbar.setNavigationIcon(R.drawable.ic_back_arrow_24px);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		add_button = findViewById(R.id.add_button);
		fStore = FirebaseFirestore.getInstance();
		fStore.collection("MasterList")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						categories = new ArrayList<>();
						for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
							String category = documentSnapshot.getId();
							categories.add(category);
							CategoryHindi categoryHindi = new CategoryHindi(documentSnapshot.getId(), documentSnapshot.getString("hindi"));
							categoryHindis.add(categoryHindi);
						}
						adapter = new ArrayAdapter<>(spinner_category.getContext(), R.layout.spinner_layout, R.id.text, categories);
						spinner_category.setAdapter(adapter);
						spinner_category.setSelection(adapter.getPosition(category));
					}
				});
		List<String> quantities = new ArrayList<>();
		quantities.add(new String("Per Kg"));
		quantities.add(new String("Per Dozen"));
		quantities.add(new String("Per Piece"));
		quantities.add(new String("Per 100 gram"));
		ArrayAdapter<String> adapter1 = new ArrayAdapter<>(quantity.getContext(), R.layout.spinner_layout, R.id.text, quantities);
		quantity.setAdapter(adapter1);
		add_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", vegetable_name.getText().toString());
				map.put("type", vegetable_type.getText().toString());
				map.put("category", spinner_category.getSelectedItem().toString());
				map.put("price", vegetable_price.getText().toString());
				map.put("code", "8886");
				if (quantity.getSelectedItem().toString().equals("Per Kg")) {
					map.put("unit", "Per Kg");
					map.put("unit_hindi", "प्रति किलो");
					List<String> quantity = new ArrayList<>();
					quantity.add("250 gram");
					quantity.add("500 gram");
					quantity.add("1 kg");
					quantity.add("2 kg");
					quantity.add("3 kg");
					quantity.add("4 kg");
					quantity.add("5 kg");
					quantity.add("10 kg");
					map.put("quantity", quantity);
				}if (quantity.getSelectedItem().toString().equals("Per Dozen")){
					map.put("unit", "Per Dozen");
					map.put("unit_hindi", "प्रति दर्जन");
					List<String> quantity = new ArrayList<>();
					quantity.add("2 pieces");
					quantity.add("4 pieces");
					quantity.add("6 pieces");
					quantity.add("8 pieces");
					quantity.add("10 pieces");
					quantity.add("12 pieces");
					quantity.add("24 pieces");
					map.put("quantity", quantity);
				}if (quantity.getSelectedItem().toString().equals("Per Piece")){
					map.put("unit", "Per Piece");
					map.put("unit_hindi", "प्रति टुकड़ा");
					List<String> quantity = new ArrayList<>();
					quantity.add("1 piece");
					quantity.add("2 pieces");
					quantity.add("3 pieces");
					quantity.add("4 pieces");
					quantity.add("5 pieces");
					map.put("quantity", quantity);
				}if (quantity.getSelectedItem().toString().equals("Per 100 gram")){
					map.put("unit", "Per 100 gram");
					map.put("unit_hindi", "प्रति 100 ग्राम");
					List<String> quantity = new ArrayList<>();
					quantity.add("100 gram");
					quantity.add("200 gram");
					quantity.add("300 gram");
					quantity.add("400 gram");
					quantity.add("500 gram");
					map.put("quantity", quantity);
				}
				map.put("type_hindi", vegetable_type_hindi.getText().toString());
				map.put("name_hindi", vegetable_name_hindi.getText().toString());
				map.put("isSelling", false);
				map.put("photo_url", "url");
				for (CategoryHindi categoryHindi : categoryHindis){
					if (categoryHindi.getCategory().equals(spinner_category.getSelectedItem().toString())){
						map.put("category_hindi", categoryHindi.getCategory_hindi());
					}
				}
				fStore.collection("MasterList")
                        .document(spinner_category.getSelectedItem().toString())
                        .collection("Products")
                        .add(map)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                            	fStore.collection("SabjiWale")
										.get()
										.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
											@Override
											public void onComplete(@NonNull Task<QuerySnapshot> task) {
												for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
													fStore.collection("SabjiWale")
															.document(documentSnapshot.getId())
															.collection("ProductsCategories")
															.document(spinner_category.getSelectedItem().toString())
															.collection("Products")
															.add(map)
															.addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
																@Override
																public void onComplete(@NonNull Task<DocumentReference> task) {
																	Toast.makeText(AddVegetableActivity.this, "Data uploaded", Toast.LENGTH_SHORT).show();
																	finish();
																}
															});
												}
											}
										});
                            }
                        });
			}
		});
	}
}