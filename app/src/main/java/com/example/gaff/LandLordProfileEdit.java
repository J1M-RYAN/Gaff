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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LandLordProfileEdit extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button back, register;
    private EditText email, password, confirmPassword, name, contactNum;
    private Map<String, String> currentDataOnDb = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_land_lord_profile_edit);

        back = (Button) findViewById(R.id.back_btn);
        register = (Button) findViewById(R.id.register_btn);
        email = (EditText) findViewById(R.id.emailTextEdit);
        password = (EditText) findViewById(R.id.passwordTextEdit);
        confirmPassword  = (EditText) findViewById(R.id.passwordTextEditConfirm);
        name = (EditText) findViewById(R.id.nameTextEdit);
        contactNum = (EditText) findViewById(R.id.contactNumberTextEdit);

        currentDataOnDb = getCurrentDataFromDB();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText() == null || password.getText() == null || confirmPassword.getText() == null ||
                        email.getText().toString().length() == 0 || password.getText().toString().length() == 0
                        || confirmPassword.getText().toString().length() == 0 || name.getText() == null ||
                        contactNum.getText() == null || name.getText().toString().length() == 0 ||
                        contactNum.getText().toString().length() == 0) {
                    Toast.makeText(LandLordProfileEdit.this, "Enter an email, password and confirm the password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tryRegister(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString(),
                            name.getText().toString(), contactNum.getText().toString());
                }
            }
        });
    }

    private HashMap<String, String> getCurrentDataFromDB(){
        HashMap<String, String> returnDataOnDb = new HashMap<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userEmail = user.getEmail().toString();
        String user_id = mAuth.getCurrentUser().getUid();
        db.collection("LandLordsDetails")
            .document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        returnDataOnDb.put("name", document.get("name").toString());
                        returnDataOnDb.put("contactNum", document.get("contactNum").toString());
                        returnDataOnDb.put("email", userEmail);
                        currentDataOnDb.put("name", document.get("name").toString());
                        currentDataOnDb.put("contactNum", document.get("contactNum").toString());
                        currentDataOnDb.put("email", userEmail);
                        email.setText(userEmail);
                        name.setText(document.get("name").toString());
                        contactNum.setText(document.get("contactNum").toString());
                    }
                }
            }
        });
        return  returnDataOnDb;

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

    public boolean validate(String email, String password, String confirm,
                            String name, String contactNum) {
        if (!isValidEmail(email)) {
            Toast.makeText(LandLordProfileEdit.this, "Invalid email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(LandLordProfileEdit.this, "Invalid password, must be at least 8 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!password.equals(confirm)){
            Toast.makeText(LandLordProfileEdit.this, "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            return  false;
        }else if (!isValidName(name)) {
            Toast.makeText(LandLordProfileEdit.this, "Invalid Name, must be alphabetical characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidContactNumber(contactNum)) {
            Toast.makeText(LandLordProfileEdit.this, "Invalid contact number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else{
            return true;
        }
    }

    public void openLandLordRegisterPg2() {
        Intent intent = new Intent(this, LandLordRegisterPg2.class);
        startActivity(intent);
    }

    private void updateEmail(String email) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LandLordProfileEdit.this, "Email Changed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updatePassword(String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LandLordProfileEdit.this, "Password Changed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateNameOrContactNum(String dataType, String data){
        String user_id = mAuth.getCurrentUser().getUid();
        db.collection("LandLordsDetails").document(user_id)
                .update(dataType, data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LandLordProfileEdit.this, dataType + "Changed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LandLordProfileEdit.this, "Firestore Error: "
                            + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tryRegister(String email, String password, String confirm,
                            String name, String contactNum) {
        if (!validate(email, password, confirm, name, contactNum)) {
            return;
        }else {
            if(currentDataOnDb.get("name").equals(name)){
                Toast.makeText(LandLordProfileEdit.this, "Name still same",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(LandLordProfileEdit.this, "Not same",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}