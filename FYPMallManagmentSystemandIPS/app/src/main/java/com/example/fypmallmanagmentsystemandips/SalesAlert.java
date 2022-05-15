package com.example.fypmallmanagmentsystemandips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SalesAlert extends AppCompatActivity {

    ListView shopSalesList;
    ArrayList<String> salesArray = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_alert);
        // Initiating Instance to firebase database.
        database = FirebaseDatabase.getInstance();
        // Creating Reference to the database Shop Sales
        DatabaseReference shpSale = database.getReference("ShopOffers");
    }
}