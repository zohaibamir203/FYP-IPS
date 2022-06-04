package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LocateMe extends AppCompatActivity {

    Spinner spnLocate, spnDestination;
    MaterialButton btnRoute;
    FirebaseDatabase database;
    ArrayList<String> currLoc = new ArrayList<>();
    ArrayList<String> DestLoc = new ArrayList<>();
    String currAddr,destAddr,currName,destName;
    String url = "http://192.168.100.20:5000";

    private String slctLocation,slctDestination;        // Selected Options in Spinner

    public String getSlctLocation() {
        return slctLocation;
    }
    public void setSlctLocation(String slctLocation) {
        this.slctLocation = slctLocation;
    }
    public String getSlctDestination() {
        return slctDestination;
    }
    public void setSlctDestination(String slctDestination) {
        this.slctDestination = slctDestination;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_me);

        // Setting Up Progress Dialog for Spinner
        ProgressDialog progressDialog = new ProgressDialog(LocateMe.this);
        progressDialog.setTitle("Loading Shops Information");
        progressDialog.show();
        // Setting Up Progress Dialog for Loading Image
        ProgressDialog progressDialog1 = new ProgressDialog(LocateMe.this);
        progressDialog1.setTitle("Fetching Map");
        progressDialog1.setMessage("It will take few seconds.");

        // Declaring Elements
        spnLocate = findViewById(R.id.spnLocate);
        spnDestination = findViewById(R.id.spnDestination);
        btnRoute = findViewById(R.id.btnRoute);

        // Setting Database Instance
        database = FirebaseDatabase.getInstance();
        // Setting Database reference to get name of Shops.
        DatabaseReference shpNameRef = database.getReference("ShopDetails");
        currLoc.add("Select Your Current Location");
        DestLoc.add("Select Your Destination Location");
        // Get data of Shop from database and set give it spinner array.
        shpNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        currLoc.add(dataSnapshot.getKey());
                        DestLoc.add(dataSnapshot.getKey());
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Current Location Adapter
        ArrayAdapter locationAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currLoc
        );
        spnLocate.setAdapter(locationAdapter);
        // Destination Location Adapter
        ArrayAdapter destinationAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                DestLoc
        );
        spnDestination.setAdapter(destinationAdapter);
        // Location Listener
        spnLocate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currName = currLoc.get(i);
                DatabaseReference currRef = database.getReference("ShopDetails").child(currName).child("shopAddress");
                currRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currAddr = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // Destination Listener
        spnDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                destName = DestLoc.get(i);
                DatabaseReference currRef = database.getReference("ShopDetails").child(destName).child("shopAddress");
                currRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        destAddr = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Map Generated when Button Is Clicked.
        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imgMap = findViewById(R.id.imgMap);
                //  Check if both current and destination address on same floor and then fetch map accordingly.
                if (currAddr.charAt(0) == destAddr.charAt(0)){
                    progressDialog1.show();
                    Picasso.get().setLoggingEnabled(true);
                    Picasso.get().load(url+"/from/"+currAddr+"/to/"+destAddr).into(imgMap);
                    progressDialog1.dismiss();
                }
                //  Check if both current and destination address on different floor and then fetch map accordingly.
                else {
                    progressDialog1.show();
                    Picasso.get().setLoggingEnabled(true);
                    Picasso.get().load(url+"/from/"+currAddr+"/to/"+destAddr+"/lift").into(imgMap);
                    progressDialog1.dismiss();
                }
            }
        });

        // Getting IP Address
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()) + ":5000/from/G-04/to/F-40/lift";
//        Toast.makeText(this, ipAddress, Toast.LENGTH_SHORT).show();
    }
}