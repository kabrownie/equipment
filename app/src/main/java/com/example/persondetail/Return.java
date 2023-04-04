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

public class Return extends AppCompatActivity {
    TextView name, price, description, phone,itemid,email;
    //    String userDtl;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        name = findViewById(R.id.item_name);
        description = findViewById(R.id.item_description);
        price = findViewById(R.id.item_price);
        phone = findViewById(R.id.item_phone);
        email =findViewById(R.id.item_email);
        itemid = findViewById(R.id.item_Id);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);

        } else {


            //Get text from Intent
            Intent intent = getIntent();
            String getName = intent.getStringExtra("Item Name");
            String getDescription = intent.getStringExtra("Item Description");
            String getPrice = intent.getStringExtra("Item Price");
            String getId = intent.getStringExtra("Item Id");
            String getemail = intent.getStringExtra("email");
            String getphone = intent.getStringExtra("Phone");

            //Set Text
            name.setText(getName);
            price.setText(getPrice);
            itemid.setText(getId);
            description.setText(getDescription);
            email.setText(getemail);
            phone.setText(getphone);






                //add to firebse
                Button add = findViewById(R.id.addItemButton);
                add.setVisibility(View.VISIBLE);
                add.setOnClickListener(new View.OnClickListener()  {

                    @Override
                    public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), test.class);
//                startActivity(intent);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        HashMap<String,String> data=new HashMap<>();
                        data.put("Item Name",name.getText().toString());
                        data.put("Item Description",description.getText().toString());
                        data.put("Item Id",itemid.getText().toString());
                        data.put("Item Price",price.getText().toString());

                        data.put("Phone",phone.getText().toString());
                        data.put("email",email.getText().toString());


                        db.collection(("in-"+user.getEmail()))
                                .document(name.getText().toString()).set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");


                                        Toast.makeText(Return.this, "Item rented.",
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

            }}}
