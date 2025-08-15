package com.prototypes.sabjiwalaanalytics.ui.master_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prototypes.sabjiwalaanalytics.MainActivity;
import com.prototypes.sabjiwalaanalytics.R;
import com.prototypes.sabjiwalaanalytics.add_vegetable.AddVegetableActivity;
import com.prototypes.sabjiwalaanalytics.classes.Vegetable;

import java.util.ArrayList;

public class MasterListFragment extends Fragment {
	
	RecyclerView recyclerView;
	TabLayout tabLayout;
	FirebaseFirestore fStore;
	ArrayList<Vegetable> vegetables;
	MasterListAdapter adapter;
	FloatingActionButton floatingActionButton;
	String tabId;
	
	public MasterListFragment() {
		//Required empty constructor
	}
	
	public static MasterListFragment newInstance(String param1, String param2) {
		MasterListFragment fragment = new MasterListFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_master_list, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		floatingActionButton = view.findViewById(R.id.floatingActionButton);
		tabLayout = view.findViewById(R.id.tab_layout);
		recyclerView = view.findViewById(R.id.recyclerView);
		((MainActivity)getActivity()).setTitle("Master List");
		
		fStore = FirebaseFirestore.getInstance();
		
		floatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddVegetableActivity.class);
				intent.putExtra("category", tabId);
				startActivity(intent);
			}
		});
		
		fStore.collection("MasterList")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
							String tabName = documentSnapshot.getId();
							tabLayout.addTab(tabLayout.newTab().setText(tabName));
						}
					}
				});
		
		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				tabId = tab.getText().toString();
				vegetables = new ArrayList<>();
				fStore.collection("MasterList")
						.document(tab.getText().toString())
						.collection("Products")
						.get()
						.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
							@Override
							public void onComplete(@NonNull Task<QuerySnapshot> task) {
								for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
									Vegetable vegetable = documentSnapshot.toObject(Vegetable.class).withId(documentSnapshot.getId());
									vegetables.add(vegetable);
								}
								adapter = new MasterListAdapter(vegetables);
								recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
								recyclerView.setAdapter(adapter);
							}
						});
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			
			}
		});
	}
}