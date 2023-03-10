package com.example.persondetail;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

   EditText Email,Password, Fname, Lname;
    MaterialButton buttonReg;
    FirebaseAuth mAuth;
    TextView loginNow;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent intent = new Intent(getApplicationContext(), view.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Fname = findViewById(R.id.fname);
        Lname = findViewById(R.id.lname);
        mAuth =FirebaseAuth.getInstance();

     loginNow = findViewById(R.id.loginNow);
       buttonReg = findViewById(R.id.signupbtn);

       loginNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Login.class);
               startActivity(intent);
               finish();
           }
       });



    buttonReg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email,  password;
            email = String.valueOf(Email.getText());
            password = String.valueOf( Password.getText());


            if(TextUtils.isEmpty(email)){
                Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT ).show();

            }

            else if(TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity.this,"Enter password",Toast.LENGTH_SHORT ).show();

            }else{
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                HashMap<String,String> data=new HashMap<>();
                data.put("Email",Email.getText().toString());
                data.put("First Name",Fname.getText().toString());
                data.put("Last Name",Lname.getText().toString());
                db.collection("Users")
                        .document(Email.getText().toString()).set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

            }



            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Account Created.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), view.class);
                                startActivity(intent);
                                finish();




                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    });

    }



}