package com.example.fypmallmanagmentsystemandips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ChangeCategory extends AppCompatActivity {

    TextView txtSelectCategory;     // Dropdown variable
    MaterialButton btnConfirm;      // Confirm Button Variable
    boolean[] selectedCategory;
    ArrayList<Integer> CategoryList = new ArrayList<>();
    ArrayList<String> CategoryTextList = new ArrayList<>();
    String[] categoryArray;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String shopName;

    // Getter and Setters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_category);

        // Initialise the variable
        txtSelectCategory = findViewById(R.id.txtSelectCategory);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Setting Up Progress Dialog
        progressDialog = new ProgressDialog(ChangeCategory.this);
        progressDialog.setTitle("Setting Categories");
        progressDialog.setMessage("Please wait a moment.");

        // Database initialisation and Getting UID,Shop Reference
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();
        DatabaseReference refUserName = database.getReference("UserShopkeeper").child(userUid).child("shopName");

        // Getting array from XML
        Resources res = getResources();
        categoryArray = res.getStringArray(R.array.shopCategory);
        selectedCategory = new boolean[categoryArray.length];

        //  Multi selector of categories and set it to Category text list array
        txtSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeCategory.this);

                // set title
                builder.setTitle("Select Category");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(categoryArray, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            CategoryList.add(i);
                            // Sort array list
                            Collections.sort(CategoryList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            CategoryList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < CategoryList.size(); j++) {
                            // concat array value
                            stringBuilder.append(categoryArray[CategoryList.get(j)]);
                            CategoryTextList.add(categoryArray[CategoryList.get(j)]);
                            Log.i("Category Selected", String.valueOf(CategoryTextList));
                            // check condition
                            if (j != CategoryList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        txtSelectCategory.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedCategory.length; j++) {
                            // remove all selection
                            selectedCategory[j] = false;
                            // clear language list
                            CategoryList.clear();
                            // clear text view value
                            txtSelectCategory.setText("");
                        }
                    }
                });
                //  Set it in database

                // show dialog
                builder.show();
            }
        });

        //Submit Button Listener and Setting Categories to Database
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Progress Dialog
                progressDialog.show();
                // Clear the category list and category text list.
                // Call Reference to get username of current user
                refUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Stored the username to global variable
                        shopName = snapshot.getValue(String.class);
                        //  Setting Category of User in Database
                        // create a ref to get a child value to see if it is null or not
                        DatabaseReference refGet = database.getReference("ShopCategories").child(shopName);
                        // getting value from above ref
                        refGet.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // checking if value is null
                                if (snapshot !=null){
                                    // if value is not null then we will delete it and store new values
                                    database.getReference("ShopCategories").child(shopName).removeValue();
                                    database.getReference("ShopCategories").child(shopName).setValue(CategoryTextList);
                                    CategoryList.clear();
                                    CategoryTextList.clear();
                                    progressDialog.dismiss();
                                    Toast.makeText(ChangeCategory.this, "Categories Updated", Toast.LENGTH_SHORT).show();
                                }else{ // Otherwise we are going to store new value if it is a null
                                    database.getReference("ShopCategories").child(shopName).setValue(CategoryTextList);
                                    CategoryList.clear();
                                    CategoryTextList.clear();
                                    progressDialog.dismiss();
                                    Toast.makeText(ChangeCategory.this, "Categories Updated", Toast.LENGTH_SHORT).show();
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
            }
        });
    }
}