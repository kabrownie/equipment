package com.example.persondetail;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import java.util.regex.Pattern;

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
            String email, password, fname, lname;
            email = String.valueOf(Email.getText());
            password = String.valueOf( Password.getText());
            fname = String.valueOf(Fname.getText());
            lname = String.valueOf(Lname.getText());

            if(TextUtils.isEmpty(fname)){
                Toast.makeText(MainActivity.this,"Enter first name",Toast.LENGTH_SHORT ).show();

            }
           else if(TextUtils.isEmpty(lname)){
                Toast.makeText(MainActivity.this,"Enter Last name",Toast.LENGTH_SHORT ).show();

            }
           else if(TextUtils.isEmpty(email)){
                Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT ).show();

            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity.this,"Enter password",Toast.LENGTH_SHORT ).show();

            }
            else if(password.length() < 8){
                Toast.makeText(MainActivity.this,"password must be more than 8 characters!",Toast.LENGTH_SHORT ).show();

            }
            else{
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                HashMap<String,String> data=new HashMap<>();
                data.put("Email",email);
                data.put("First Name",fname);
                data.put("Last Name",Lname.getText().toString());
                data.put("password",Password.getText().toString());

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
