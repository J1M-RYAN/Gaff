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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RenterRegisterPg2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button back, register;
    private EditText name, contactNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db =  FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_renter_register_pg2);

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
            public void onClick(View view){
                if (name.getText() == null || contactNum.getText() == null ||
                        name.getText().toString().length() == 0 ||
                        contactNum.getText().toString().length() == 0) {
                    Toast.makeText(RenterRegisterPg2.this, "Enter a name and contact number",
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
            Toast.makeText(RenterRegisterPg2.this, "Invalid Name, must be alphabetical characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidContactNumber(contactNum)) {
            Toast.makeText(RenterRegisterPg2.this, "Invalid contact number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    public void openRenterLogin() {
        Intent intent = new Intent(this, RenterLogin.class);
        startActivity(intent);
    }


    private void tryRegister(String name, String contactNum) {
        if (!validate(name, contactNum)) {
            return;
        }else{
            String user_id = mAuth.getCurrentUser().getUid();
            updateUserDetails(name, contactNum, user_id);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    openRenterLogin();
                }
            }, 1000);
        };
    }

    private void updateUserDetails(String name, String contactNum, String user_id) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", name);
        userDetails.put("contactNum", contactNum);
        db.collection("RentersDetails").document(user_id).update(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RenterRegisterPg2.this, "Details Updated",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RenterRegisterPg2.this, "Firestore Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
