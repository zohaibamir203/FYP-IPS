package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fypmallmanagmentsystemandips.Models.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAdd extends AppCompatActivity {

    MaterialButton btnAdminSubmit;
    TextInputLayout txtInputAdminEmail, txtInputAddPassword;
    String adminEmail,adminPassword;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(AdminAdd.this);
        progressDialog.setTitle("Creating Admin Account");
        progressDialog.setMessage("We are creating your account.");

        btnAdminSubmit = findViewById(R.id.btnAdminSubmit);
        txtInputAdminEmail = findViewById(R.id.textInputAdminEmail);
        txtInputAddPassword = findViewById(R.id.txtInputPassword);

        btnAdminSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                registerNewAdmin();
            }
        });

        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(AdminAdd.this,AdminPanel.class);
            startActivity(intent);
        }
    }

    public void registerNewAdmin(){
        adminEmail = txtInputAdminEmail.getEditText().getText().toString();
        adminPassword = txtInputAddPassword.getEditText().getText().toString();
        Log.i("Email", adminEmail);
        Log.i("Password",adminPassword);
        mAuth.createUserWithEmailAndPassword(adminEmail, adminPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Admin admin = new Admin(adminEmail,adminPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("UsersAdmin").child(id).setValue(admin);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(AdminAdd.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAdd.this,AdminPanel.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AdminAdd.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}