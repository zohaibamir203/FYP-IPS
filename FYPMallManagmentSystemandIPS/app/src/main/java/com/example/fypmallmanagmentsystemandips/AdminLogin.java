package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {

    MaterialButton btnAdminLogin, btnAdminAdd;
    TextInputLayout txtInputAdminAdd, txtInputAddPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(AdminLogin.this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Signing in to your Account.");


        txtInputAdminAdd = findViewById(R.id.txtInputAdminAdd);
        txtInputAddPassword = findViewById(R.id.txtInputAddPassword);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnAdminAdd = findViewById(R.id.btnAdminAdd);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                loginAdmin();
            }
        });
        btnAdminAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLogin.this,AdminAdd.class);
                startActivity(intent);
            }
        });
    }

    public void loginAdmin(){
        String adminEmail,adminPassword;
        adminEmail = txtInputAdminAdd.getEditText().getText().toString();
        adminPassword = txtInputAddPassword.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(adminEmail, adminPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(AdminLogin.this,AdminPanel.class);
                            startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AdminLogin.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}