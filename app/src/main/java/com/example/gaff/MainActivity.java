package com.example.gaff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    private Button landlord, renter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        landlord = (Button) findViewById(R.id.landlord_btn);
        renter = (Button) findViewById(R.id.renter_btn);

        landlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLandlordLogin();
            }
        });

        renter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRenterLogin();
            }
        });

    }

    public void openLandlordLogin(){
        Intent intent = new Intent(this, LandLordLogin.class);
        startActivity(intent);
    }

    public void openRenterLogin(){
        Intent intent = new Intent(this, RenterLogin.class);
        startActivity(intent);
    }
}