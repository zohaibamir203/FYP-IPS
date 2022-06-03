package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fypmallmanagmentsystemandips.Models.Shop;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeName extends AppCompatActivity {

    MaterialButton btnSubmit;
    TextInputLayout txtInputName;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        progressDialog = new ProgressDialog(ChangeName.this);
        progressDialog.setTitle("Changing Shop Name");

        btnSubmit = findViewById(R.id.btnSubmit);
        txtInputName = findViewById(R.id.txtInputName);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();
        DatabaseReference refUserName = database.getReference("UserShopkeeper").child(userUid).child("shopName");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                refUserName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String shpName = snapshot.getValue(String.class);
                        database.getReference("ShopDetails").child(shpName).child("shopName").setValue(txtInputName.getEditText().getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                progressDialog.dismiss();
                Toast.makeText(ChangeName.this, "Successfully Changed the Name.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}