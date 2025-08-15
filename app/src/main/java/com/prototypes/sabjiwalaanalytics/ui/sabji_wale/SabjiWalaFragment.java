package com.prototypes.sabjiwalaanalytics.ui.sabji_wale;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prototypes.sabjiwalaanalytics.MainActivity;
import com.prototypes.sabjiwalaanalytics.R;
import com.prototypes.sabjiwalaanalytics.classes.Shop;

import java.util.ArrayList;

public class SabjiWalaFragment extends Fragment {
    
    RecyclerView recyclerView;
    SabjiWalaAdapter adapter;
    FirebaseFirestore fStore;
    ArrayList<Shop> shops;
    
    public SabjiWalaFragment() {
        // Required empty public constructor
    }
    
    public static SabjiWalaFragment newInstance() {
        SabjiWalaFragment fragment = new SabjiWalaFragment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sabji_wala, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        fStore = FirebaseFirestore.getInstance();
        shops = new ArrayList<>();
        fStore.collection("SabjiWale")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            Shop shop = documentSnapshot.toObject(Shop.class).withId(documentSnapshot.getId());
                            shops.add(shop);
                        }
                        ((MainActivity)getActivity()).setTitle(String.valueOf(shops.size()) + " shops");
                        adapter = new SabjiWalaAdapter(shops);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}