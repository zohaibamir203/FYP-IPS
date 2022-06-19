package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fypmallmanagmentsystemandips.Models.Shop;
import com.example.fypmallmanagmentsystemandips.Models.Shopkeeper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddShop extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    TextInputLayout txtInputShopName, txtInputAddShopEmail,txtInputAddShopPassword;
    MaterialButton btnAdd;
    Spinner spnAddress;
    String[] address;
    String selectedAddress,ShopName,ShopMail,ShopPass;


    // Getter and Setter
    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Firebase initialisation
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Setting Progress Dialog
        progressDialog = new ProgressDialog(AddShop.this);
        progressDialog.setTitle("Creating Shopkeeper Account");
        progressDialog.setMessage("We are creating your account.");

        // Address Array Resource
        Resources res = getResources();
        address = res.getStringArray(R.array.addressList);

//        // Initialization of XML Elements
        txtInputShopName = findViewById(R.id.txtInputShopName);
        txtInputAddShopEmail = findViewById(R.id.txtInputAddShopEmail);
        spnAddress = findViewById(R.id.spnAddress);
        txtInputAddShopPassword = findViewById(R.id.txtInputAddShopPassword);
        btnAdd = findViewById(R.id.btnAdd);
        
        // Array Address Setup of Adapter
        ArrayAdapter<String> addressAdapter = new ArrayAdapter(AddShop.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,address);
        spnAddress.setAdapter(addressAdapter);
        
        // Address Listener and Setting Value of Selected Address from the drop down
        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedAddress(address[i]);
                Log.i("Address", getSelectedAddress());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        
        // Add Shop Listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                ShopName = txtInputShopName.getEditText().getText().toString();
                ShopMail = txtInputAddShopEmail.getEditText().getText().toString();
                ShopPass = txtInputAddShopPassword.getEditText().getText().toString();
                mAuth.createUserWithEmailAndPassword(ShopMail, ShopPass)
                        .addOnCompleteListener(AddShop.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Shopkeeper shopkeeper = new Shopkeeper(ShopName,ShopMail,ShopPass,getSelectedAddress());
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("UserShopkeeper").child(id).setValue(shopkeeper);
                                    Shop shop = new Shop(ShopName,getSelectedAddress(),id);
                                    database.getReference().child("ShopDetails").child(ShopName).setValue(shop);
                                    Toast.makeText(AddShop.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(AddShop.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

}