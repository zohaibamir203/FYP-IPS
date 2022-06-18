package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.fypmallmanagmentsystemandips.Adapters.SalesRecViewAdapter;
import com.example.fypmallmanagmentsystemandips.Models.Sales;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SalesAlert extends AppCompatActivity {

    ListView shopSalesList;
    ArrayList<String> salesArray = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView salesRec;
    MaterialButton btnShowOnMap;
    ArrayList<String> shopName = new ArrayList<>();
    ArrayList<String> shopOffers = new ArrayList<>();
    ArrayList<ArrayList> test = new ArrayList<>();
    int shopNameSize;
    int iterator;
    String shpNm;
    String shpOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_alert);

        // Setting Up Progress Dialog for Spinner
        ProgressDialog progressDialog = new ProgressDialog(SalesAlert.this);
        progressDialog.setTitle("Loading Shops Information");
        ProgressDialog progressDialog1 = new ProgressDialog(SalesAlert.this);
        progressDialog1.setTitle("Loading Sales Offers");
        progressDialog.show();

        // Initialising View Elements
        salesRec = findViewById(R.id.salesRec);
        btnShowOnMap = findViewById(R.id.btnShowOnMap);

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
                Log.d("Shop Names : ", String.valueOf(shopName));
                Log.d("Shop Offers : ", String.valueOf(test));
            }
        });

//        sales.add(new Sales("Nike","Upto 60% off."));
//        sales.add(new Sales("Puma","Upto 60% off."));
//        sales.add(new Sales("Adidas","Upto 60% off."));
//        sales.add(new Sales("Mcdonalds","Buy one get one free"));
//        sales.add(new Sales("Airlink",("Upto 10% off."+"\n"+"Hello")));
//        // Sales Adapter
//        SalesRecViewAdapter adapter = new SalesRecViewAdapter();
//        adapter.setSales(sales);
//        salesRec.setAdapter(adapter);
//        salesRec.setLayoutManager(new LinearLayoutManager(SalesAlert.this));
    }
}