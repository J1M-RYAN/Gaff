package com.example.gaff;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandLordLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;

    private Button register, back, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lord_login);
        mAuth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.register_btn);
        back = (Button) findViewById(R.id.back);
        login = (Button) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.emailTextEdit);
        password = (EditText) findViewById(R.id.passwordTextEditLogin);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLandLordRegister();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText() == null || password.getText() == null || email.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                    Toast.makeText(LandLordLogin.this, "Enter an email and password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tryLogin(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    public void openLandLordRegister() {
        Intent intent = new Intent(this, LandLordRegister.class);
        startActivity(intent);
    }

    public void openLandLordHome() {
        Intent intent = new Intent(this, LandLordHome.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
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

    public boolean validate(String email, String password) {
        if (!isValidEmail(email)) {
            Toast.makeText(LandLordLogin.this, "Invalid email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(LandLordLogin.this, "Invalid password, must be at least 8 characters",
                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void tryLogin(String email, String password) {
        if (!validate(email, password)) {
            return;
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LandLordLogin.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        openLandLordHome();
                                    }
                                }, 1000);
                            } else {
                                Toast.makeText(LandLordLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}