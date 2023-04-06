package com.example.persondetail;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class PaymentActivity extends AppCompatActivity {

    private EditText phoneNumberInput;
    private EditText amountInput;
    private Button payButton;
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
        email = findViewById(R.id.item_email);
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

            setContentView(R.layout.activity_payment);

            phoneNumberInput = findViewById(R.id.phone_number_input);
            amountInput = findViewById(R.id.amount_input);
            payButton = findViewById(R.id.pay_button);

            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_payment);

                    phoneNumberInput = findViewById(R.id.phone_number_input);
                    amountInput = findViewById(R.id.amount_input);
                    payButton = findViewById(R.id.pay_button);

                    TextView paybutton2 = findViewById(R.id.pay_button);
                    paybutton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phoneNumber = phoneNumberInput.getText().toString();
                            String amount = amountInput.getText().toString();
//////////

                            // Implement your payment logic here
                            new HttpAsyncTask(phoneNumber , amount).execute("https://tinybitdaraja.herokuapp.com/msp");
                            //////////////

                        }
                    });



                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                        HashMap<String,String> data=new HashMap<>();
                        data.put("Item Name",name.getText().toString()+" (Returned");
                        data.put("Item Description",description.getText().toString());
                        data.put("Item Id",itemid.getText().toString());
                        data.put("Item Price",price.getText().toString());
                        data.put("Phone",phone.getText().toString());
                        data.put("email",email.getText().toString());
                    data.put("status",email.getText().toString());




                    db.collection(("in-"+user.getEmail()))
                                .document(itemid.getText().toString()+ name.getText().toString()).set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");


                                        Toast.makeText(PaymentActivity.this, "data sent.",
                                                Toast.LENGTH_SHORT).show();

                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), view.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);

                                    }
                                });

                    //

                }
            });
        }
    }






    public static   String POST( String number , String amount ) {
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL("https://tinybitdaraja.herokuapp.com/msp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer mgN_9p2k2ROS2Gb3gfA6H");
            connection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("amount", amount);
            jsonObject.accumulate("number", number);
            jsonObject.accumulate("private_key", "13c7ab60-776c-4d7c-8c4f-ace73eced83b");

            String json = jsonObject.toString();

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private String number , amount ;

        public HttpAsyncTask(String number, String amount) {
            this.number = number;
            this.amount = amount;
        }

        @Override
        protected String doInBackground(String... urls) {
            return POST(this.number , this.amount);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "STK PUSH SENT!", Toast.LENGTH_LONG).show();
        }
    }
}
