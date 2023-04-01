package com.example.persondetail;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class Description extends AppCompatActivity {
    TextView name, price, description;
    TextView userDtl;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        name = findViewById(R.id.item_name);
        description = findViewById(R.id.item_description);
        price = findViewById(R.id.item_price);
        auth = FirebaseAuth.getInstance();
        userDtl = findViewById(R.id.userdetail);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);

        } else {
            userDtl.setText(user.getEmail());

        //Get text from Intent
        Intent intent = getIntent();
        String getName = intent.getStringExtra("name");
        String getDescription = intent.getStringExtra("description");
        String getPrice = intent.getStringExtra("price");
        //Set Text
        name.setText(getName);
        price.setText(getPrice);
        description.setText(getDescription);
    }

        //add to firebse
        Button logout = findViewById(R.id.addItemButton);
        logout.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), test.class);
//                startActivity(intent);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                HashMap<String,String> data=new HashMap<>();
                data.put("Name",name.getText().toString());
                data.put("Price",price.getText().toString());
                data.put("Description",description.getText().toString());

                db.collection((Objects.requireNonNull(user.getEmail())))
                        .document(name.getText().toString()).set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");


                                    Toast.makeText(Description.this, "Item rented.",
                                            Toast.LENGTH_SHORT).show();

                                    finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

            }
        });

}}
