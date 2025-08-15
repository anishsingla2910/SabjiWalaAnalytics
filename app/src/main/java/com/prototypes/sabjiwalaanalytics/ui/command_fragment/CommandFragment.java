package com.prototypes.sabjiwalaanalytics.ui.command_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.prototypes.sabjiwalaanalytics.R;

public class CommandFragment extends Fragment {
    
    Button command;
    FirebaseFirestore fStore;
    
    public CommandFragment() {
        // Required empty public constructor
    }
    
    public static CommandFragment newInstance(String param1, String param2) {
        CommandFragment fragment = new CommandFragment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_command, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fStore = FirebaseFirestore.getInstance();
        command = view.findViewById(R.id.command_button);
        command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
            }
        });
    }
}