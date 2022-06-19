package com.example.fypmallmanagmentsystemandips;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class CustomerMenu extends AppCompatActivity {

    MaterialButton btnLocateMe,btnSalesAlert,btnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        btnLocateMe = findViewById(R.id.btnLocateMe);
        btnSalesAlert = findViewById(R.id.btnSalesAlert);
        btnCategories = findViewById(R.id.btnCategories);

        btnLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerMenu.this,LocateMe.class);
                startActivity(intent);
            }
        });
        btnSalesAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerMenu.this,SalesAlert.class);
                startActivity(intent);
            }
        });
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerMenu.this,Categories.class);
                startActivity(intent);
            }
        });
    }
}