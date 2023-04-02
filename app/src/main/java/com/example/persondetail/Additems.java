package com.example.persondetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class Additems extends AppCompatActivity {
    // Firebase Variables
    FirebaseDatabase realTimeDb;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                } else {
                    Item itemInformation = new Item( itemName , itemDescription , itemPrice , itemPhoneNumber);
                    reference.setValue(itemInformation);
                    reference.child("items").child(itemID).child("item").setValue(itemInformation);
                    //addDatatoFirebase(itemName, itemPhoneNumber, itemDescription);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // inside the method of on Data change we are setting
                            // our object class to our database reference.
                            // data base reference will sends data to firebase.
                            // Get new data information

                            // after adding this data we are showing toast message.
                            Toast.makeText(Additems.this, "data added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // if the data is not added or it is cancelled then
                            // we are displaying a failure toast message.
                            Toast.makeText(Additems.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}