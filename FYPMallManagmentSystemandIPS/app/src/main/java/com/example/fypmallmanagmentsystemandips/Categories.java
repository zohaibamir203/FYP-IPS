package com.example.fypmallmanagmentsystemandips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    Spinner spnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        spnCategories = findViewById(R.id.spnCategories);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Select Category");
        categories.add("Category 1");
        categories.add("Category 2");
        categories.add("Category 3");
        categories.add("Category 4");
        categories.add("Category 5");
        categories.add("Category 6");
        categories.add("Category 7");

        ArrayAdapter categoriesAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        spnCategories.setAdapter(categoriesAdapter);

    }
}