package com.example.gaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LandLordRegister extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button back, register;
    private EditText email, password, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_land_lord_register);

        back = (Button) findViewById(R.id.back_btn);
        register = (Button) findViewById(R.id.register_btn);
        email = (EditText) findViewById(R.id.emailTextEdit);
        password = (EditText) findViewById(R.id.passwordTextEdit);
        confirmPassword  = (EditText) findViewById(R.id.passwordTextEditConfirm);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText() == null || password.getText() == null || confirmPassword.getText() == null || email.getText().toString().length() == 0 || password.getText().toString().length() == 0 || confirmPassword.getText().toString().length() == 0) {
                    Toast.makeText(LandLordRegister.this, "Enter an email, password and confirm the password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tryRegister(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
                }
            }
        });
    }
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isValidPassword(String s) {
        if (s == null || s.length() < 8) {
            return false;
        }
        return true;
    }

    public boolean validate(String email, String password, String confirm) {
        if (!isValidEmail(email)) {
            Toast.makeText(LandLordRegister.this, "Invalid email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(LandLordRegister.this, "Invalid password, must be at least 8 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!password.equals(confirm)){
            Toast.makeText(LandLordRegister.this, "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            return  false;
        }else{
            return true;
        }
    }

    public void openLandLordRegisterPg2() {
        Intent intent = new Intent(this, LandLordRegisterPg2.class);
        startActivity(intent);
    }

    private void addUserIdToLandLordsCollection(String user_id) {
        Map<String, Object> details = new HashMap<>();
        details.put("landlordId", user_id);
        db.collection("LandlordsUserIds").document(user_id).set(details);
    }

    private void addUserDetails(String name, String contactNum, String user_id) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", name);
        userDetails.put("contactNum", contactNum);
        db.collection("LandLordsDetails").document(user_id).set(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LandLordRegister.this, "Details Successfully Added",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LandLordRegister.this, "Firestore Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tryRegister(String email, String password, String confirm) {
        if (!validate(email, password, confirm)) {
            return;
        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();
                                addUserIdToLandLordsCollection(user_id);
                                addUserDetails("", "", user_id);
                                Toast.makeText(LandLordRegister.this, "Account created.",
                                        Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        openLandLordRegisterPg2();
                                    }
                                }, 1000);
                            } else {
                                Toast.makeText(LandLordRegister.this, "Account creation failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}