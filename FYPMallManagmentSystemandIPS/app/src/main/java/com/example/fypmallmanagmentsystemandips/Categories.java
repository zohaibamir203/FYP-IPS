package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fypmallmanagmentsystemandips.Adapters.CategoriesRecViewAdapter;
import com.example.fypmallmanagmentsystemandips.Models.Category;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    Spinner spnCategories;
    MaterialButton btnSelect;
    String[] categories1;
    ArrayList<String> shopName = new ArrayList<>();
    ArrayList<String> categoryArray = new ArrayList<>();
    ArrayList<String> test = new ArrayList<>();
    String categorySelected;
    int shopNameSize;
    RecyclerView categoryRecycler;
    FirebaseDatabase database;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        // Setting Up Progress Dialog for Spinner
        ProgressDialog progressDialog = new ProgressDialog(Categories.this);
        progressDialog.setTitle("Loading Shops Information");
        progressDialog.show();
        ProgressDialog progressDialog1 = new ProgressDialog(Categories.this);
        progressDialog1.setTitle("Loading Shops Categories");

        // Declaring Elements
        spnCategories = findViewById(R.id.spnCategories);
        btnSelect = findViewById(R.id.btnSelect);
        categoryRecycler = findViewById(R.id.categoryRecycler);

        // Loading Data to Spinner
        categories1 = getResources().getStringArray(R.array.shopCategory);
        ArrayAdapter categoriesAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories1
        );
        spnCategories.setAdapter(categoriesAdapter);
        // Recycler View Array List
        ArrayList<Category> categories = new ArrayList<>();
        // Setting Database Instance
        database = FirebaseDatabase.getInstance();
        // Setting Database reference to get name of Shops.
        DatabaseReference shpNameRef = database.getReference("ShopCategories");
        // Store Category which is Selected will get stored in categorySelected and also fetch Categories from database of each shop.
        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorySelected = categories1[i];
                progressDialog1.show();
                test.clear();
                // Fetching Categories of Shop and Check if it falls in our category.
                for (int x = 0; x < shopNameSize; x++){
                    position = x;
                    Log.d("1", String.valueOf(position));
                    DatabaseReference categoriesRef = database.getReference("ShopCategories").child(shopName.get(x));
                    categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null){
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    categoryArray.add((String) dataSnapshot.getValue());
                                }
                                test.add(String.valueOf(categoryArray));
                                categoryArray.clear();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                progressDialog1.dismiss();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Gets All Shops Name which are in ShopCategories.
        shpNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        shopName.add(dataSnapshot.getKey());
                        shopNameSize++;
                        progressDialog.dismiss();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Check Which Shops are in Specific Category.
        // Select button click listener
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categories.clear();
                Log.d("Categories from Database", String.valueOf(test));
                for (int i = 0;i < test.size();i++){
                    if (test.get(i).contains(categorySelected)){
                        categories.add(new Category(shopName.get(i)));
                    }
                }
                // Categories Adapter
                CategoriesRecViewAdapter adapter = new CategoriesRecViewAdapter();
                adapter.setCategory(categories);
                categoryRecycler.setAdapter(adapter);
                categoryRecycler.setLayoutManager(new LinearLayoutManager(Categories.this));
            }
        });
//        categories.add(new Category("McDonalds"));
//        categories.add(new Category("KFC"));
//        categories.add(new Category("Johny Jugnu"));

    }
}