package com.example.persondetail.tabfragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persondetail.Additems;
import com.example.persondetail.ItemModel;
import com.example.persondetail.ItemsAdapter;
import com.example.persondetail.Login;
import com.example.persondetail.MainActivity;
import com.example.persondetail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class myItems extends Fragment {


    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentz
        View view = inflater.inflate(R.layout.my_items, container, false);
        Button additems= view.findViewById(R.id.addItemButton);
        additems.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Additems.class);
                startActivity(intent);

            }
        });
// Get reference

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(view.VISIBLE);

        RecyclerView itemRV = view.findViewById(R.id.item_recycle);
TextView logout = view.findViewById(R.id.logout);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Toast.makeText(getContext(),"logout success",Toast.LENGTH_SHORT ).show();
            }
        });

//retreave items from firebase
        ArrayList<ItemModel> itemModelArrayList = new ArrayList<ItemModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("out-"+user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //TODO: add loading button
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                itemModelArrayList.add(new ItemModel(document.getString("Item Name"), document.getString("Item Price"), document.getString("Item Description"),
                                        document.getString("Phone"),document.getString("Item Id"),document.getString("email")));
                                ItemsAdapter itemAdapter = new ItemsAdapter(getContext(), itemModelArrayList);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                                itemRV.setLayoutManager(linearLayoutManager);
                                itemRV.setAdapter(itemAdapter);
                                progressBar.setVisibility(view.GONE);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }


                    }
                });



        return view;}}