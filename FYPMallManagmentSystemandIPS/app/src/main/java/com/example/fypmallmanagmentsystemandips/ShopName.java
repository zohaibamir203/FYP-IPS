package com.example.fypmallmanagmentsystemandips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShopName extends AppCompatActivity {

    Spinner spnCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_name);

        spnCurrentLocation = findViewById(R.id.spnCurrentLocation);

        ArrayList<String> locations = new ArrayList<>();
        locations.add("Select Your Current Location");
        locations.add("Nike");
        locations.add("Adidas");
        locations.add("Puma");
        locations.add("Reebok");
        locations.add("Junaid Jamshed");
        locations.add("Pasha");
        locations.add("Mcdonald's");
        locations.add("Uniworth");

        ArrayAdapter locationAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                locations
        );

        spnCurrentLocation.setAdapter(locationAdapter);

    }


}