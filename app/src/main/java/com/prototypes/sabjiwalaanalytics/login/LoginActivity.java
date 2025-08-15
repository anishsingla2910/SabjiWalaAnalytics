package com.prototypes.sabjiwalaanalytics.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.prototypes.sabjiwalaanalytics.MainActivity;
import com.prototypes.sabjiwalaanalytics.R;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    private EditText editText;
    private Button btnContinue;
    ImageView tick_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        fAuth = FirebaseAuth.getInstance();
        
        if (fAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        editText = findViewById(R.id.editTextMobile);
        tick_image = findViewById(R.id.check_image);
        tick_image.setVisibility(View.INVISIBLE);
        btnContinue = findViewById(R.id.buttonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();
                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Please enter Valid number");
                    editText.requestFocus();
                    return;
                }
                String phoneNumber = "+91" + number;
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);
                finish();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("9810165395") || s.toString().equals("9205055395")) {
                    btnContinue.setEnabled(true);
                    tick_image.setVisibility(View.VISIBLE);
                } else {
                    btnContinue.setEnabled(false);
                    tick_image.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}