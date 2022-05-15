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

public class ShopkeeperLogin extends AppCompatActivity {

    TextInputLayout txtInputShopEmail, txtInputPassword;
    MaterialButton btnShopkeeperLogin;
    String shopMail,shopPass;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_login);

        progressDialog = new ProgressDialog(ShopkeeperLogin.this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Signing in to your Account");

        mAuth = FirebaseAuth.getInstance();

        txtInputShopEmail = findViewById(R.id.txtInputAddShopEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnShopkeeperLogin = findViewById(R.id.btnShopkeeperLogin);

        btnShopkeeperLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopMail = txtInputShopEmail.getEditText().getText().toString();
                shopPass = txtInputPassword.getEditText().getText().toString();
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(shopMail, shopPass)
                        .addOnCompleteListener(ShopkeeperLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(ShopkeeperLogin.this,ShopkeeperMenu.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(ShopkeeperLogin.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}