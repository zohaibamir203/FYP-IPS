package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RemoveShop extends AppCompatActivity {

    RelativeLayout parentResult;  // Relative Layout of Result Box
    TextInputLayout txtInputSearch;     // User Text Input
    MaterialButton btnSearch,btnShopRemove;     // Button of Search and Removal of shop.
    TextView searchShopName, searchShopAddress;     // Display of Shop name and address.
    FirebaseDatabase database;      // Database
    ProgressDialog progressDialog,progressDialog1;      // Dialog for responsiveness.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_shop);

        database = FirebaseDatabase.getInstance();      // Creating instance of Database

        // Initialisation of all XML element
        txtInputSearch = findViewById(R.id.txtInputSearch);
        btnSearch = findViewById(R.id.btnSearch);
        parentResult = findViewById(R.id.parentResult);
        searchShopName = findViewById(R.id.searchShopName);
        searchShopAddress = findViewById(R.id.searchShopAddress);
        btnShopRemove = findViewById(R.id.btnShopRemove);

        // Setting up content for Dialog Box
        progressDialog = new ProgressDialog(RemoveShop.this);
        progressDialog1 = new ProgressDialog(RemoveShop.this);
        progressDialog.setTitle("Searching " + txtInputSearch.getEditText().getText().toString());
        progressDialog1.setTitle("Removing " + txtInputSearch.getEditText().getText().toString());
        progressDialog.setMessage("Please wait a moment.");

        // Searching and Setting the Result of Search.
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();      // Showing Dialog Box of searching.
                // shopAddRef is getting shopAddress from ShopDetails > (Shop Name) > Address
                DatabaseReference shopAddRef = database.getReference("ShopDetails").child(txtInputSearch.getEditText().getText().toString()).child("shopAddress");
                // shopNameRef is getting shopName from ShopDetails > (Shop Name) > Name
                DatabaseReference shopNameRef = database.getReference("ShopDetails").child(txtInputSearch.getEditText().getText().toString()).child("shopName");
                // Reading Value from Add Ref and setting it to Result box Address Element
                shopAddRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        searchShopAddress.setText("Shop Address: " + value);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                // Reading Value from Name Ref and setting it to Result box Name Element
                shopNameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        searchShopName.setText("Shop Name: " + value);
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                // Make the Result Box visible
                parentResult.setVisibility(View.VISIBLE);
            }
        });

        // Removal of Shop
        btnShopRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog1.show();     // Showing the Dialog of Removing
                //  shopIdRef is getting shopUID from ShopDetails > (Shop Name) > UID
                DatabaseReference shopIdRef = database.getReference("ShopDetails").child(txtInputSearch.getEditText().getText().toString()).child("shopUID");
                //  Reading Value of UID from shop id Reference and then removing it from UserShopKeeper.
                shopIdRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Value = snapshot.getValue(String.class);
                        // It just check that returning value of snapshot is null or not.
                        if (Value == null){
                            Log.i("Null Remove Shop", "Null Value");
                        }else{
                            //  Creating reference of Shop from UserShopkeeper so that it can be removed.
                            DatabaseReference rmvIdRef = database.getReference("UserShopkeeper").child(Objects.requireNonNull(Value));
                            //  removing the data from remIdRef reference.
                            rmvIdRef.removeValue();
                        }
                        //  Creating reference of the Shop in ShopDetails so that it can be deleted
                        DatabaseReference shopNameRef = database.getReference("ShopDetails").child(txtInputSearch.getEditText().getText().toString());
                        //  Removing Shop using shopNameRef
                        shopNameRef.removeValue();
                        // Creating reference of Shop from ShopCategories so that it can be removed.
                        DatabaseReference rmvCategRef = database.getReference("ShopCategories").child(txtInputSearch.getEditText().getText().toString());
                        //  removing the data from rmvCategRef reference.
                        rmvCategRef.removeValue();
                        progressDialog1.dismiss();
                        Toast.makeText(RemoveShop.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                progressDialog.dismiss();
            }
        });
    }
}