package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageSales extends AppCompatActivity {

    MaterialButton btnAddSale;
    TextInputLayout txtSaleInput;
    String shopName;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<String> offerArray = new ArrayList<>();
    ArrayList<String> offerList = new ArrayList<>();
    ListView offerListView;
    ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        // Initialise View Elements
        btnAddSale = findViewById(R.id.btnAddSale);
        txtSaleInput = findViewById(R.id.txtSaleInput);
        //Initialise Database and getting current signed in user id
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();
        Log.i("User Id", userUid);
        // Creating reference to current sign in user shop name
        DatabaseReference refUserName = database.getReference("UserShopkeeper").child(userUid).child("shopName");

        progressDialog1 = new ProgressDialog(ManageSales.this);
        progressDialog1.setTitle("Adding Sale Off");


        // Add Button for Adding the Offers to Database
        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog1.show();
                // Retreiving the shop name of signed in user
                refUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        shopName = snapshot.getValue(String.class);
                        // Creating reference to the Shop offers of current signed in shop.
                        DatabaseReference refOffer = database.getReference("ShopOffers").child(shopName);
                        // Retreiving and storing offers to shop.
                        refOffer.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getValue() != null){
                                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        String oldSales = dataSnapshot.getValue(String.class);
                                        offerArray.add(oldSales);
                                        offerList.add(oldSales);
                                        Log.v("Value",dataSnapshot.getValue(String.class));
                                    }
                                    offerArray.add(txtSaleInput.getEditText().getText().toString());
                                    offerList.add(txtSaleInput.getEditText().getText().toString());
                                    database.getReference("ShopOffers").child(shopName).setValue(offerArray);
                                    offerArray.clear();
                                    progressDialog1.dismiss();
                                }else{
                                    offerArray.add(txtSaleInput.getEditText().getText().toString());
                                    offerList.add(txtSaleInput.getEditText().getText().toString());
                                    Log.i("offerArray", txtSaleInput.getEditText().getText().toString());
                                    database.getReference("ShopOffers").child(shopName).setValue(offerArray);
                                    offerArray.clear();
                                    progressDialog1.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                offerListView = findViewById(R.id.offerListView);
//                ArrayAdapter<String> saleAdapter = new ArrayAdapter<>(ManageSales.this, android.R.layout.simple_list_item_1,offerList);
//                offerListView.setAdapter(saleAdapter);
//                offerList.clear();
//                progressDialog1.dismiss();
            }
        });

    }
}