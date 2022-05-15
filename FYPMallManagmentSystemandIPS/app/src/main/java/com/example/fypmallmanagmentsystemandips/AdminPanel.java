package com.example.fypmallmanagmentsystemandips;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    Button btnAddShop, btnRmvShop;
    MaterialButton btnAdminSignOut;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        progressDialog = new ProgressDialog(AdminPanel.this);
        progressDialog.setTitle("Signing Out");
        progressDialog.setMessage("Singing you out from your account");

        btnAddShop = findViewById(R.id.btnAddShop);
        btnRmvShop = findViewById(R.id.btnRmvShop);
        btnAdminSignOut = findViewById(R.id.btnAdminSignOut);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanel.this,AddShop.class);
                startActivity(intent);
            }
        });
        btnRmvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanel.this,RemoveShop.class);
                startActivity(intent);
            }
        });
        btnAdminSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                mAuth.signOut();
                progressDialog.dismiss();
                Intent intent = new Intent(AdminPanel.this,AdminLogin.class);
                startActivity(intent);
            }
        });
    }
}