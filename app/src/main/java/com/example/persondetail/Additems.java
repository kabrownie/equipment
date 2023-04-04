package com.example.persondetail;

import static android.content.ContentValues.TAG;

import static com.google.firebase.database.ServerValue.TIMESTAMP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class Additems extends AppCompatActivity {
    // Firebase Variables
    FirebaseDatabase realTimeDb;
    DatabaseReference reference;

    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);
        // declare variables
        EditText name = findViewById(R.id.item_name_input);
        EditText description = findViewById(R.id.item_description_input);
        EditText price = findViewById(R.id.item_price_input);
        EditText number = findViewById(R.id.phone_number_input);
        EditText id = findViewById(R.id.item_id_input);
        Button add = findViewById(R.id.add_item_button);

        // initialize firebase variables
        realTimeDb = FirebaseDatabase.getInstance();
        reference =  realTimeDb.getReference("ItemInfo");

        // function to push data to firestore


        // add click event to button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the data to strings
                String itemName = name.getText().toString();
                String itemDescription = description.getText().toString();
                String itemPrice = price.getText().toString();
                String itemPhoneNumber = number.getText().toString();
                String itemID = id.getText().toString();


//              // push data to firebase
                // below line is for checking whether the
                // edittext fields are empty or not.
                // else call the method to add
                // data to our database.
                if (TextUtils.isEmpty(itemName) && TextUtils.isEmpty(itemDescription) && TextUtils.isEmpty(itemPrice)  && TextUtils.isEmpty(itemPhoneNumber)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(Additems.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                }

                else {


                    ///firestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseFirestore db0 = FirebaseFirestore.getInstance();
                    //check if id exist
                    DocumentReference docRef = db0.collection("out-"+user.getEmail()).document(itemID+user.getEmail());

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(Additems.this, "You have an item with that id,", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Additems.this, "Please select an id you haven't used before", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    ///firestore
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    FirebaseFirestore db1 = FirebaseFirestore.getInstance();

                                    HashMap<String,String> data=new HashMap<>();
                                    data.put("Item Name",itemName);
                                    data.put("Item Description",itemDescription);
                                    data.put("Item Id",itemID);
                                    data.put("Item Price","Ksh"+itemPrice);
                                    data.put("Phone",itemPhoneNumber);
                                    data.put("email",user.getEmail());




                                    db.collection("All Items")
                                            .document(itemID+user.getEmail()).set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Toast.makeText(Additems.this, "Item Posted",
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
                                    db1.collection("out-"+user.getEmail())
                                            .document(itemID+user.getEmail()).set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Toast.makeText(Additems.this, "Item Posted",
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

                                }

                            };



                        });

                }};
        });}}




