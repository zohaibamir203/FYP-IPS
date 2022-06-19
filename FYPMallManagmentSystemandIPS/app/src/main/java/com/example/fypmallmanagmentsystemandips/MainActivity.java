package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    MaterialButton btnCustomer,btnShopkeeper,btnAdmin;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        btnCustomer = findViewById(R.id.btnCustomer);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnShopkeeper = findViewById(R.id.btnShopkeeper);

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomerMenu.class);
                startActivity(intent);
            }
        });

        btnShopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ShopkeeperLogin.class);
                startActivity(intent);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
            }
        });

        // Write a message to the database
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//        Log.i("My Ref", String.valueOf(myRef));
//
//        myRef.setValue("Hello! FYP Database.");
//
//        // Retrieving message from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }
}