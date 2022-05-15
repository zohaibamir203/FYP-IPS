package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopkeeperMenu extends AppCompatActivity {

    MaterialButton btnChangeName,btnManageSales,btnChangeCategories,btnShopkeeperSignOut;
    TextView txtShopkeeperName;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String ShopName;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_menu);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        txtShopkeeperName = findViewById(R.id.txtShopkeeperName);
        btnChangeName = findViewById(R.id.btnChangeName);
        btnManageSales = findViewById(R.id.btnManageSales);
        btnChangeCategories = findViewById(R.id.btnChangeCategories);
        btnShopkeeperSignOut = findViewById(R.id.btnShopkeeperSignOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopkeeperMenu.this,ChangeName.class);
                startActivity(intent);
            }
        });
        btnManageSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopkeeperMenu.this,ManageSales.class);
                startActivity(intent);
            }
        });
        btnChangeCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopkeeperMenu.this,ChangeCategory.class);
                startActivity(intent);
            }
        });
        btnShopkeeperSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(ShopkeeperMenu.this,ShopkeeperLogin.class);
                startActivity(intent);
            }
        });
    }
}
