package com.example.persondetail.tabfragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.persondetail.ItemModel;

import com.example.persondetail.ItemsAdapter2;
import com.example.persondetail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class rented extends Fragment {


    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentz
        View view = inflater.inflate(R.layout.rented, container, false);

// Get reference
//
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(view.VISIBLE);
        RecyclerView itemRV = view.findViewById(R.id.item_recycle);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

//retreave items from firebase
        ArrayList<ItemModel> itemModelArrayList = new ArrayList<ItemModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("in-"+user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //TODO: add loading button
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                itemModelArrayList.add(new ItemModel(document.getString("Item Name"), document.getString("Item Price"), document.getString("Item Description"),
                                        document.getString("Phone"),document.getString("Item Id"),document.getString("email")));
                                ItemsAdapter2 itemAdapter = new ItemsAdapter2(getContext(), itemModelArrayList);

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