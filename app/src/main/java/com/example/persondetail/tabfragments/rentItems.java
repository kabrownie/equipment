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
import android.widget.TextView;

import com.example.persondetail.ItemModel;
import com.example.persondetail.ItemsAdapter;
import com.example.persondetail.Login;
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

public class rentItems extends Fragment {


    FirebaseAuth auth;
//    MaterialButton logout;

    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentz
        View view = inflater.inflate(R.layout.rent_item, container, false);

// Get reference
//

        RecyclerView itemRV = view.findViewById(R.id.item_recycle);

        auth = FirebaseAuth.getInstance();
       // logout = view.findViewById(R.id.logout);
        user = auth.getCurrentUser();
//


//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getActivity(), Login.class);
//                startActivity(intent);
//
//
//
//
//            }
//        });

        ArrayList<ItemModel> itemModelArrayList = new ArrayList<ItemModel>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("TestItems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                itemModelArrayList.add(new ItemModel(document.getString("Name"), document.getString("Price"), document.getString("Description")));
                                ItemsAdapter itemAdapter = new ItemsAdapter(getContext(), itemModelArrayList);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                                itemRV.setLayoutManager(linearLayoutManager);
                                itemRV.setAdapter(itemAdapter);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }


                    }
                });

// when  button is clicked

        return view;
    }}
//        Button logout = view.findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), test.class);
//                startActivity(intent);
//
//            }
//        });

