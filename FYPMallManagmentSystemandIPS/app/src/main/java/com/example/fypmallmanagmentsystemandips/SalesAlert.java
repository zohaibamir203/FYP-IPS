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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.fypmallmanagmentsystemandips.Adapters.SalesRecViewAdapter;
import com.example.fypmallmanagmentsystemandips.Models.Sales;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SalesAlert extends AppCompatActivity {

    ListView shopSalesList;
    ArrayList<String> salesArray = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView salesRec;
    MaterialButton btnShowOnMap;
    ArrayList<String> shopName = new ArrayList<>();
    ArrayList<String> shopOffers = new ArrayList<>();
    TouchImageView imgSales;
    int shopNameSize;
    int iterator;
    String shpNm;
    String shpOff = "";
    String ShopAddress;
    String url = "http://192.168.100.20:5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_alert);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#443CB6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        // Setting Up Progress Dialog for Spinner
        ProgressDialog progressDialog = new ProgressDialog(SalesAlert.this);
        progressDialog.setTitle("Loading Shops Information");
        ProgressDialog progressDialog1 = new ProgressDialog(SalesAlert.this);
        progressDialog1.setTitle("Loading Sales Offers");
        progressDialog.show();
        // Setting Up Progress Dialog for Loading Image
        ProgressDialog progressDialog2 = new ProgressDialog(SalesAlert.this);
        progressDialog2.setTitle("Fetching Map");
        progressDialog2.setMessage("It will take few seconds.");

        // Initialising View Elements
        salesRec = findViewById(R.id.salesRec);
        btnShowOnMap = findViewById(R.id.btnShowOnMap);
        imgSales = findViewById(R.id.imgSales);

        // Initiating Instance to firebase database.
        database = FirebaseDatabase.getInstance();

        // Recycler View array list.
        ArrayList<Sales> sales = new ArrayList<>();

        // Creating Reference to the get Shop Names
        DatabaseReference shpSale = database.getReference("ShopOffers");
        // Fetch Names of Shops From Database who are offering
        shpSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        shopName.add(dataSnapshot.getKey());
                        shopNameSize++;
                        progressDialog.dismiss();
                    }
                    // Fetching Shop Sales Offering
                    iterator = 0;
                    progressDialog1.show();
                    for (int i =0; i < shopNameSize; i++){
                        DatabaseReference categoriesRef = database.getReference("ShopOffers").child(shopName.get(i));
                        categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot != null){
                                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        shopOffers.add((String) dataSnapshot.getValue());
                                    }
                                    // Fetched Shop Name and There Respective Offers
                                    if (iterator < shopName.size()){
                                        shpNm = shopName.get(iterator);
                                        iterator++;
                                    }
                                    for (int x = 0; x < shopOffers.size();x++){
                                        if (shopOffers.size() > 1){
                                            shpOff = shpOff + shopOffers.get(x) + "\n";
                                            if (shopOffers.size()-1 == x)
                                            {
                                                sales.add(new Sales(shpNm,shpOff));
                                                shpOff = "";
                                            }
                                        }else{
                                            shpOff = shopOffers.get(x);
                                            sales.add(new Sales(shpNm,shpOff));
                                            shpOff = "";
                                        }
                                    }
                                    shopOffers.clear();
                                }
                                // Sales Adapter
                                SalesRecViewAdapter adapter = new SalesRecViewAdapter();
                                adapter.setSales(sales);
                                salesRec.setAdapter(adapter);
                                salesRec.setLayoutManager(new LinearLayoutManager(SalesAlert.this));
                                progressDialog1.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog2.show();
                ShopAddress = "";
                for (int i = 0; i < shopName.size(); i++){
                    DatabaseReference addrRef = database.getReference("ShopDetails").child(shopName.get(i)).child("shopAddress");
                    int finalI = i;
                    addrRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null){
                                ShopAddress = ShopAddress + snapshot.getValue();
                            }
                            if (finalI == shopName.size()-1){
                                Picasso.get().setLoggingEnabled(true);
                                Picasso.get().load(url+"/from/"+ShopAddress).into(imgSales);
                                progressDialog2.dismiss();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}