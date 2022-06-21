package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.example.fypmallmanagmentsystemandips.Adapters.ManageSalesRecViewAdapter;
import com.example.fypmallmanagmentsystemandips.Models.Manage;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ManageSales extends AppCompatActivity {

    MaterialButton btnAddSale,btnDelete;
    TextInputLayout txtSaleInput;
    String shopName;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<String> offerArray = new ArrayList<>();
    ArrayList<String> offerList = new ArrayList<>();
    ListView offerListView;
    ProgressDialog progressDialog1,progressDialog2;
    RecyclerView addSaleRec;
    ArrayList<Manage> manage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sales);
        //Configuring and Setting Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Setting Up Color of Action Bar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        // Initialise View Elements
        btnAddSale = findViewById(R.id.btnAddSale);
        txtSaleInput = findViewById(R.id.txtSaleInput);
        addSaleRec = findViewById(R.id.addSaleRec);
        //Initialise Database and getting current signed in user id
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userUid = user.getUid();
        Log.i("User Id", userUid);
        // Recycle View Array
        // Setting Up Dialog Box.
        progressDialog1 = new ProgressDialog(ManageSales.this);
        progressDialog1.setTitle("Adding Sale Off");
        progressDialog2 = new ProgressDialog(ManageSales.this);
        progressDialog2.setTitle("Fetching Data");
        progressDialog2.show();
        // Creating reference to current sign in user shop name
        DatabaseReference refUserName = database.getReference("UserShopkeeper").child(userUid).child("shopName");
        // Fetching Shop Name from Database.
        refUserName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopName = snapshot.getValue().toString();
                Log.d("Shop Name",shopName);
                DatabaseReference offers = database.getReference("ShopOffers").child(shopName);
                offers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot != null){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                manage.add(new Manage(dataSnapshot.getValue(String.class)));
                            }
                            ManageSalesRecViewAdapter adapter = new ManageSalesRecViewAdapter();
                            adapter.setShopOffers(manage);
                            addSaleRec.setAdapter(adapter);
                            addSaleRec.setLayoutManager(new LinearLayoutManager(ManageSales.this));
                            progressDialog2.dismiss();
                        }else{
                            Toast.makeText(ManageSales.this, "You Have No Sales to Offer.", Toast.LENGTH_SHORT).show();
                            progressDialog2.dismiss();
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

        // Add Button for Adding the Offers to Database
        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog1.show();
                manage.clear();
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
                                manage.add(new Manage(oldSales));
                                Log.v("Value",dataSnapshot.getValue(String.class));
                            }
                            manage.add(new Manage(txtSaleInput.getEditText().getText().toString()));
                            offerArray.add(txtSaleInput.getEditText().getText().toString());
                            offerList.add(txtSaleInput.getEditText().getText().toString());
                            database.getReference("ShopOffers").child(shopName).setValue(offerArray);
                            offerArray.clear();
                            ManageSalesRecViewAdapter adapter = new ManageSalesRecViewAdapter();
                            adapter.setShopOffers(manage);
                            addSaleRec.setAdapter(adapter);
                            addSaleRec.setLayoutManager(new LinearLayoutManager(ManageSales.this));
                            progressDialog1.dismiss();
                        }else{
                            manage.add(new Manage(txtSaleInput.getEditText().getText().toString()));
                            offerArray.add(txtSaleInput.getEditText().getText().toString());
                            offerList.add(txtSaleInput.getEditText().getText().toString());
                            Log.i("offerArray", txtSaleInput.getEditText().getText().toString());
                            database.getReference("ShopOffers").child(shopName).setValue(offerArray);
                            offerArray.clear();
                            ManageSalesRecViewAdapter adapter = new ManageSalesRecViewAdapter();
                            adapter.setShopOffers(manage);
                            addSaleRec.setAdapter(adapter);
                            addSaleRec.setLayoutManager(new LinearLayoutManager(ManageSales.this));
                            progressDialog1.dismiss();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}