package com.prototypes.sabjiwalaanalytics.ui.customers;

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
import com.prototypes.sabjiwalaanalytics.classes.Customer;

import java.util.ArrayList;

public class CustomersFragment extends Fragment {
    
    RecyclerView recyclerView;
    CustomersAdapter adapter;
    FirebaseFirestore fStore;
    ArrayList<Customer> customers;
    
    public CustomersFragment() {
        // Required empty public constructor
    }
    
    public static CustomersFragment newInstance(String param1, String param2) {
        CustomersFragment fragment = new CustomersFragment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        customers = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("Customers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            Customer customer = documentSnapshot.toObject(Customer.class).withId(documentSnapshot.getId());
                            customers.add(customer);
                        }
                        ((MainActivity)getActivity()).setTitle(String.valueOf(customers.size()) + " customers");
                        adapter = new CustomersAdapter(customers);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}