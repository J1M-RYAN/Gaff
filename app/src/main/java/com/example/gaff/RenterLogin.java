package com.example.gaff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RenterLogin extends AppCompatActivity {

    Button back, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_login);
        back = (Button) findViewById(R.id.back);
        register = (Button) findViewById(R.id.register_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                goToRenterRegister();
            }
        });
    }

    private void goToRenterRegister(){
        Intent i = new Intent(this, RenterHome.class);
        startActivity(i);
    }
}