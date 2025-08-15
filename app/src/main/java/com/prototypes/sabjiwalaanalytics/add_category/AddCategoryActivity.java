package com.prototypes.sabjiwalaanalytics.add_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prototypes.sabjiwalaanalytics.MainActivity;
import com.prototypes.sabjiwalaanalytics.R;

import java.util.HashMap;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {
    
    FirebaseFirestore fStore;
    Button add_button;
    TextInputLayout category, category_hindi;
    Toolbar toolbar = findViewById(R.id.toolbar);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        
        fStore = FirebaseFirestore.getInstance();
        add_button = findViewById(R.id.add_button);
        category = findViewById(R.id.category);
        category_hindi = findViewById(R.id.category_hindi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Category");
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("hindi", category_hindi.getEditText().getText().toString());
                fStore.collection("MasterList")
                        .document(category.getEditText().getText().toString())
                        .set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                fStore.collection("SabjiWale")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                    fStore.collection("SabjiWale")
                                                            .document(documentSnapshot.getId())
                                                            .collection("ProductsCategories")
                                                            .document(category.getEditText().getText().toString())
                                                            .set(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(AddCategoryActivity.this, "Task completing", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        });
            }
        });
    }
}