package com.example.gaff;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LandLordRegister2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button back, register;
    private EditText name, contactNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_land_lord_register);

        back = (Button) findViewById(R.id.back_btn);
        register = (Button) findViewById(R.id.register_btn);
        name = (EditText) findViewById(R.id.nameTextEdit);
        contactNum = (EditText) findViewById(R.id.contactNumberTextEdit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText() == null || contactNum.getText() == null || 
                name.getText().toString().length() == 0 || 
                contactNum.getText().toString().length() == 0) {
                    Toast.makeText(LandLordRegister2.this, "Enter a name and contact number",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tryRegister(name.getText().toString(), contactNum.getText().toString());
                }
            }
        });
    }

    public boolean isValidContactNumber(String num) {
        return Patterns.PHONE.matcher(num).matches();
    }

    public boolean isValidName(String name) {
        if(!name.matches("[a-zA-Z ]+"))  
        {
            return false;
        }
        return true;
    }

    public boolean validate(String name, String contactNum) {
        if (!isValidName(name)) {
            Toast.makeText(LandLordRegister2.this, "Invalid Name, must be alphabetical characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidContactNumber(contactNum)) {
            Toast.makeText(LandLordRegister2.this, "Invalid contact number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    public void openLandLordLogin() {
        Intent intent = new Intent(this, LandLordLogin.class);
        startActivity(intent);
    }


    private void tryRegister(String name, String contactNum) {
        if (!validate(name, contactNum)) {
            return;
        }else{
            Toast.makeText(LandLordRegister2.this, "Account created.",
                    Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    openLandLordLogin();
                }
            }, 1000);
        };
    }
}